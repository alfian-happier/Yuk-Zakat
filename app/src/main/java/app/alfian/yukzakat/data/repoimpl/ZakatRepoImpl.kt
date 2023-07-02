package app.alfian.yukzakat.data.repoimpl

import app.alfian.yukzakat.data.model.Anggota
import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.data.repo.ZakatRepo
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.util.getZakatTypeByName
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Alfian on 6/13/2023.
 */

class ZakatRepoImpl(val activity : BaseActivity) : ZakatRepo {

    override fun payZakat(user: User, zakat: Zakat): Observable<String> {
        val subject : BehaviorSubject<String> = BehaviorSubject.create()

        val zakatToPayment = hashMapOf(
            "full_name" to zakat.fullName,
            "bank_account_name" to zakat.bankAccountName,
            "nominal" to zakat.nominal,
            "phone_number" to zakat.phoneNumber,
            "transfer_photo_base64" to zakat.transferPhotoBase64,
            "type" to zakat.type.name,
            "date" to zakat.date,
            "username" to user.username,
            "anggota" to Gson().toJson(zakat.anggota)
        )

        activity.db.collection("zakat")
            .add(zakatToPayment)
            .addOnSuccessListener {
                subject.onNext("Success Zakat Payment")
                subject.onComplete()
            }
            .addOnFailureListener {
                subject.onError(it)
            }

        return subject
    }

    override fun getZakatHistory(user: User): Observable<List<Zakat>> {
        val subject : BehaviorSubject<List<Zakat>> = BehaviorSubject.create()

        activity.db.collection("zakat")
            .get()
            .addOnSuccessListener { result ->
                val zakatHistory = arrayListOf<Zakat>()
                for (zakat in result){
                    if (user.username == zakat.get("username") as String){
                        getZakatTypeByName(zakat.get("type") as String)?.let { zakatType ->
                            val itemZakat = Zakat(
                                fullName = zakat.get("full_name") as String,
                                bankAccountName = zakat.get("bank_account_name") as String,
                                nominal = zakat.get("nominal") as String,
                                phoneNumber = zakat.get("phone_number") as String,
                                transferPhotoBase64 = zakat.get("transfer_photo_base64") as String,
                                type = zakatType,
                                date = zakat.get("date") as String,
                                anggota = Gson().fromJson(zakat.get("anggota") as String,object : TypeToken<List<Anggota>>(){}.type)
                            )
                            zakatHistory.add(itemZakat)
                        }
                    }
                }
                subject.onNext(zakatHistory)
                subject.onComplete()
            }
            .addOnFailureListener {
                subject.onError(it)
            }

        return subject
    }
}