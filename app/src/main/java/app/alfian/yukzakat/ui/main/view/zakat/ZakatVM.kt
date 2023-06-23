package app.alfian.yukzakat.ui.main.view.zakat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.model.Anggota
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.data.model.ZakatType
import app.alfian.yukzakat.data.repoimpl.ZakatRepoImpl
import app.alfian.yukzakat.data.usecase.PayZakatUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.copyString
import app.alfian.yukzakat.util.isMoreEqualsThan
import app.alfian.yukzakat.util.toStringDate
import app.alfian.yukzakat.util.toast
import java.util.Date
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class ZakatVM @Inject constructor(val activity: BaseActivity) : BaseVM(){

    private val zakatRepo = ZakatRepoImpl(activity)
    val payZakatData = MutableLiveData<PayZakatUseCase.PayZakatResult>()

    val isZakatFitrah = ObservableField<Boolean>()
    val isShodaqoh = ObservableField<Boolean>()

    val title = ObservableField<String>()
    val minimalTransaction = ObservableField<String>()
    val bankAccountNumberMosque = ObservableField<String>()
    val bankAccountNameMosque = ObservableField<String>()

    val nominal = ObservableField<String>()
    val fullname = ObservableField<String>()
    val phoneNumber = ObservableField<String>()
    val bankAccountName = ObservableField<String>()
    val transferPhotoBase64 = ObservableField<String>()
    val zakatType = ObservableField<ZakatType>()
    val anggota = arrayListOf<Anggota>()

    val layoutType = MutableLiveData<String>() // default || form || payment || prove || success

    init {
        changeLayout("default")
        isZakatFitrah.set(false)
        isShodaqoh.set(false)
        activity.db.collection("utils")
            .get()
            .addOnSuccessListener {
                val utils = it.documents
                if (utils.isNotEmpty()){
                    val util = utils[0]
                    bankAccountNumberMosque.set(util.get("receiver_bank_number") as String)
                    bankAccountNameMosque.set(util.get("receiver_bank_name") as String)
                }
            }
            .addOnFailureListener {  }
    }

    fun changeLayout(type : String){
        when(type){
            "form" -> {
                if (nominal.get().isNullOrBlank()){
                    activity.toast("Masukkan 'Nominal' dengan benar")
                    return
                }
                if (!nominal.get()!!.isMoreEqualsThan(minimalTransaction.get()!!)){
                    activity.toast("Minimal Transaksi adalah Rp. ${minimalTransaction.get()!!}")
                    return
                }
            }
            "payment" -> {
                if (fullname.get().isNullOrBlank()){
                    activity.toast("Masukkan 'Nama Lengkap' dengan benar")
                    return
                }
                if (phoneNumber.get().isNullOrBlank()){
                    activity.toast("Masukkan 'Nomor Handphone / Whatsapp' dengan benar")
                    return
                }
            }
            "success" -> {
                if (bankAccountName.get().isNullOrBlank()){
                    activity.toast("Masukkan 'Nama Pemilik Rekening' dengan benar")
                    return
                }
                if (transferPhotoBase64.get().isNullOrBlank()){
                    activity.toast("Pilih foto 'Bukti Pembayaran' dengan benar")
                    return
                }
            }
        }
        if (type != "success")
            layoutType.postValue(type)
        else
            onUploadBuktiClicked()

    }

    fun onCopyBankNumberClicked(){
        bankAccountNumberMosque.get()?.copyString(activity)
    }

    private fun onUploadBuktiClicked(){
        SharedSession.user?.let { user ->
            val zakat = Zakat(
                fullName = fullname.get()!!,
                nominal = nominal.get()!!,
                phoneNumber = phoneNumber.get()!!,
                bankAccountName = bankAccountName.get()!!,
                transferPhotoBase64 = transferPhotoBase64.get()!!,
                date = Date().toStringDate(),
                type = zakatType.get()!!,
                anggota = anggota
            )
            PayZakatUseCase(zakatRepo).execute(user,zakat)
                .compose(applyObservableAsync())
                .subscribe { payZakatData.postValue(it) }
                .addTo(disposable)
        }
    }

    fun onHomeClicked(){
        activity.changeActivity(HomeActivity())
    }

    fun addAnggota(){
        anggota.add(Anggota())
    }

    fun removeAnggota(position: Int){
        anggota.removeAt(position)
    }
}