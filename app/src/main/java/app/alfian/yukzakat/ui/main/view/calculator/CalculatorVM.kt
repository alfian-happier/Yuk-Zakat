package app.alfian.yukzakat.ui.main.view.calculator

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.data.model.ZakatType
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.zakat.ZakatActivity
import app.alfian.yukzakat.util.getCleanDoubleOrNull
import app.alfian.yukzakat.util.goToActivity
import app.alfian.yukzakat.util.toRupiahFormat
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/23/2023.
 */

class CalculatorVM @Inject constructor(val activity: BaseActivity) : BaseVM(){

    val pendapatanPerBulan = ObservableField<String>()
    val bonusTHRDanLainnya = ObservableField<String>()
    val hasilPenghasilan = ObservableField<String>()

    val uangTunaiTabunganDeposito = ObservableField<String>()
    val nilaiEmasPerakPermata = ObservableField<String>()
    val kendaraanRumahAset = ObservableField<String>()
    val hutangCicilan = ObservableField<String>()
    val hasilMaal = ObservableField<String>()

    val layoutType = MutableLiveData<String>() // option || penghasilan || maal || penghasilan_calculated || maal_calculated

    init {
        changeLayout("option")
    }

    fun changeLayout(type : String){
        when(type){
            "penghasilan" -> {
                pendapatanPerBulan.set(null)
                bonusTHRDanLainnya.set(null)
                hasilPenghasilan.set(null)
            }
            "penghasilan_calculated" -> calculatePenghasilan()
            "maal" -> {
                uangTunaiTabunganDeposito.set(null)
                nilaiEmasPerakPermata.set(null)
                kendaraanRumahAset.set(null)
                hutangCicilan.set(null)
                hasilMaal.set(null)
            }
            "maal_calculated" -> calculateMaal()
        }
        if (!type.contains("calculated"))
            layoutType.postValue(type)
    }

    fun payZakat(type : String){
        when(type){
            "penghasilan" -> {
                activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_PENGHASILAN,hasilPenghasilan.get()))
                activity.finish()
            }
            "maal" -> {
                activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_MAAL,hasilMaal.get()))
                activity.finish()
            }
        }
    }

    private fun calculatePenghasilan(){
        activity.hideKeyboard()
        if (pendapatanPerBulan.get().isNullOrBlank()){
            activity.toast("Masukkan 'Jumlah Pendapatan per bulan' dengan benar")
            return
        }
        if (bonusTHRDanLainnya.get().isNullOrBlank()){
            activity.toast("Masukkan 'Bonus, THR, dan lainnya' dengan benar")
            return
        }
        pendapatanPerBulan.get()!!.getCleanDoubleOrNull()?.also { input1 ->
            bonusTHRDanLainnya.get()!!.getCleanDoubleOrNull()?.also { input2 ->
                val total = input1 + input2
                val calculated = total * 0.025
                hasilPenghasilan.set(calculated.toRupiahFormat())
                layoutType.postValue("penghasilan_calculated")
            }
        }
    }

    private fun calculateMaal(){
        activity.hideKeyboard()
        if (uangTunaiTabunganDeposito.get().isNullOrBlank()){
            activity.toast("Masukkan 'Uang tunai, tabungan, deposito' dengan benar")
            return
        }
        if (nilaiEmasPerakPermata.get().isNullOrBlank()){
            activity.toast("Masukkan 'Nilai emas, perak, dan atau permata' dengan benar")
            return
        }
        if (kendaraanRumahAset.get().isNullOrBlank()){
            activity.toast("Masukkan 'Kendaraan, rumah, aset lain' dengan benar")
            return
        }
        uangTunaiTabunganDeposito.get()!!.getCleanDoubleOrNull()?.also { input1 ->
            nilaiEmasPerakPermata.get()!!.getCleanDoubleOrNull()?.also { input2 ->
                kendaraanRumahAset.get()!!.getCleanDoubleOrNull()?.also { input3 ->
                    if (hutangCicilan.get().isNullOrBlank())
                        hutangCicilan.set("Rp0,00")
                    hutangCicilan.get()!!.getCleanDoubleOrNull()?.also { input4 ->
                        val total = input1 + input2 + input3 + input4
                        val calculated = total * 0.025
                        hasilMaal.set(calculated.toRupiahFormat())
                        layoutType.postValue("maal_calculated")
                    }
                }
            }
        }
    }
}