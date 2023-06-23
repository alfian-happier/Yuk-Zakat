package app.alfian.yukzakat.data.repoimpl

import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.repo.UserRepo
import app.alfian.yukzakat.data.util.toUser
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.util.decrypt
import app.alfian.yukzakat.util.encrypt
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Zharfan on 6/13/2023.
 */

class UserRepoImpl(val activity : BaseActivity) : UserRepo {

    override fun login(username: String, password: String): Observable<User> {
        val subject : BehaviorSubject<User> = BehaviorSubject.create()

        activity.db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (user in result) {
                    if (username == user.get("username") as String) {
                        if (password == decrypt(user.get("password") as String)) {
                            subject.onNext(user.toUser())
                            subject.onComplete()
                            return@addOnSuccessListener
                        } else {
                            subject.onError(Exception("Invalid Password"))
                            return@addOnSuccessListener
                        }
                    }
                }
                subject.onError(Exception("Invalid Username"))
            }.addOnFailureListener {
                subject.onError(it)
            }
        return subject
    }

    override fun register(username: String, fullname: String, email: String, password: String): Observable<User> {
        val subject : BehaviorSubject<User> = BehaviorSubject.create()
        val userToRegister = hashMapOf(
            "email" to email,
            "fullname" to fullname,
            "username" to username,
            "display_pict_base64" to "",
            "password" to encrypt(password)
        )

        activity.db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (user in result) {
                    if (username == user.get("username") as String) {
                        subject.onError(Exception("Username already used"))
                        return@addOnSuccessListener
                    }
                }
                activity.db.collection("users")
                    .add(userToRegister)
                    .addOnSuccessListener {
                        it.get().addOnSuccessListener { registeredUser ->
                            subject.onNext(registeredUser.toUser())
                            subject.onComplete()
                            return@addOnSuccessListener
                        }
                    }
                    .addOnFailureListener {
                        subject.onError(it)
                    }
            }.addOnFailureListener {
                subject.onError(it)
            }
        return subject
    }

    override fun editProfile(fullname: String, email: String, displayPictBase64: String): Observable<User> {
        val subject : BehaviorSubject<User> = BehaviorSubject.create()
        val fieldToUpdate = mapOf(
            "email" to email,
            "fullname" to fullname,
            "display_pict_base64" to displayPictBase64,
        )
        SharedSession.user?.let { user ->
            activity.db.collection("users")
                .document(user.id)
                .update(fieldToUpdate)
                .addOnSuccessListener {
                    subject.onNext(User(user.id,user.username,fullname,email,displayPictBase64))
                    subject.onComplete()
                }
                .addOnFailureListener {
                    subject.onError(it)
                }
        }
        return subject
    }

    override fun changePassword(currentPassword: String, newPassword: String): Observable<String> {
        val subject : BehaviorSubject<String> = BehaviorSubject.create()
        val fieldToUpdate = mapOf(
            "password" to encrypt(newPassword),
        )
        SharedSession.user?.let { user ->
            activity.db.collection("users")
                .document(user.id)
                .get()
                .addOnSuccessListener { resultUser ->
                    if (currentPassword == decrypt(resultUser.get("password") as String)){
                        activity.db.collection("users")
                            .document(user.id)
                            .update(fieldToUpdate)
                            .addOnSuccessListener {
                                subject.onNext("Success Change Password.")
                                subject.onComplete()
                            }
                            .addOnFailureListener {
                                subject.onError(it)
                            }
                    } else {
                        subject.onError(Exception("Wrong Input Old Password."))
                        return@addOnSuccessListener
                    }
                }
                .addOnFailureListener {
                    subject.onError(it)
                }
        }
        return subject
    }
}