package app.alfian.yukzakat.data.repo

import app.alfian.yukzakat.data.model.User
import io.reactivex.Observable

/**
 * Created by Alfian on 6/13/2023.
 */

interface UserRepo {
    fun login(username: String, password: String) : Observable<User>
    fun register(username: String, fullname: String, email: String, password: String) : Observable<User>
    fun editProfile(fullname: String, email: String, displayPictBase64: String) : Observable<User>
    fun changePassword(currentPassword: String, newPassword: String) : Observable<String>
}