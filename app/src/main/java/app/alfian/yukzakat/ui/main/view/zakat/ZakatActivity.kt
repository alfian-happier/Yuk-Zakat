package app.alfian.yukzakat.ui.main.view.zakat

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.model.ZakatType
import app.alfian.yukzakat.data.usecase.PayZakatUseCase
import app.alfian.yukzakat.databinding.ActivityZakatBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.zakat.adapter.AnggotaListAdapter
import app.alfian.yukzakat.util.addRupiahFormatter
import app.alfian.yukzakat.util.gone
import app.alfian.yukzakat.util.invisible
import app.alfian.yukzakat.util.loadFromBase64
import app.alfian.yukzakat.util.visible
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class ZakatActivity : BaseActivity(){

    companion object {
        private var type : ZakatType? = null
        private var preNominal : String? = null

        fun instance(type: ZakatType, preNominal : String? = null) : ZakatActivity {
            Companion.type = type
            Companion.preNominal = preNominal
            return ZakatActivity()
        }
    }

    @Inject lateinit var vm : ZakatVM
    private lateinit var binding : ActivityZakatBinding
    private var adapter : AnggotaListAdapter? = null

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zakat)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        binding.inputNominal.addRupiahFormatter()
        settingLayout()
        binding.buttonPilihFoto.setOnClickListener {
            pickImage()
        }
        vm.payZakatData.observe(this){
            when(it){
                is PayZakatUseCase.PayZakatResult.Success -> {
                    vm.layoutType.postValue("success")
                }
                is PayZakatUseCase.PayZakatResult.Failure -> {
                    it.error.printStackTrace()
                }
            }
        }
        vm.onBackObservable.observe(this){
            if (it){
                onBackPressed()
            }
        }
    }

    private fun settingLayout(){
        type?.let { zakatType ->
            vm.zakatType.set(zakatType)
            when(zakatType){
                ZakatType.ZAKAT_PENGHASILAN -> {
                    vm.title.set("Zakat Penghasilan")
                    vm.minimalTransaction.set("20.000")
                    binding.bannerZakat.setImageResource(R.drawable.banner_penghasilan)
                    binding.bannerDoaZakat.setImageResource(R.drawable.doa_zakat_penghasilan)
                }
                ZakatType.ZAKAT_FITRAH -> {
                    vm.title.set("Zakat Fitrah")
                    vm.minimalTransaction.set("50.000")
                    vm.isZakatFitrah.set(true)
                    binding.bannerZakat.setImageResource(R.drawable.banner_fitrah)
                    binding.bannerDoaZakat.setImageResource(R.drawable.doa_zakat_fitrah)
                    binding.recyclerAnggota.layoutManager = LinearLayoutManager(this)
                    adapter = AnggotaListAdapter(vm.anggota){ position ->
                        vm.removeAnggota(position)
                        adapter?.notifyItemRemoved(position)
                    }
                    binding.buttonAddAnggota.setOnClickListener {
                        vm.addAnggota()
                        adapter?.notifyItemInserted(vm.anggota.size - 1)
                    }
                    binding.recyclerAnggota.adapter = adapter
                }
                ZakatType.ZAKAT_MAAL -> {
                    vm.title.set("Zakat Maal")
                    vm.minimalTransaction.set("100.000")
                    binding.bannerZakat.setImageResource(R.drawable.banner_maal)
                    binding.bannerDoaZakat.setImageResource(R.drawable.doa_zakat_maal)
                }
                ZakatType.SHODAQOH -> {
                    vm.title.set("Shodaqoh")
                    vm.minimalTransaction.set("10.000")
                    vm.isShodaqoh.set(true)
                    binding.bannerZakat.setImageResource(R.drawable.banner_shodaqoh)
                    binding.bannerDoaZakat.setImageResource(0)
                }
            }
        }
        preNominal?.let {
            vm.nominal.set(it)
        }
        vm.layoutType.observe(this){
            when(it){
                "default" -> {
                    type?.let {zakatType ->
                        vm.title.set(zakatType.stringName)
                    }
                    binding.layoutNominalZakat.visible()
                    binding.layoutFormZakat.gone()
                    binding.layoutPaymentZakat.gone()
                    binding.layoutPaymentProveZakat.gone()
                    binding.layoutSuccessZakat.gone()
                }
                "form" -> {
                    vm.title.set("Isi Form")
                    binding.layoutNominalZakat.gone()
                    binding.layoutFormZakat.visible()
                    binding.layoutPaymentZakat.gone()
                    binding.layoutPaymentProveZakat.gone()
                    binding.layoutSuccessZakat.gone()
                    type?.let {zakatType ->
                        if (zakatType == ZakatType.ZAKAT_FITRAH) binding.layoutAnggota.visible()
                    }
                }
                "payment" -> {
                    vm.title.set("Bank Transfer")
                    binding.layoutNominalZakat.gone()
                    binding.layoutFormZakat.gone()
                    binding.layoutPaymentZakat.visible()
                    binding.layoutPaymentProveZakat.gone()
                    binding.layoutSuccessZakat.gone()
                }
                "prove" -> {
                    vm.title.set("Bukti Pembayaran")
                    binding.layoutNominalZakat.gone()
                    binding.layoutFormZakat.gone()
                    binding.layoutPaymentZakat.gone()
                    binding.layoutPaymentProveZakat.visible()
                    binding.layoutSuccessZakat.gone()
                }
                "success" -> {
                    vm.title.set("Transaksi Lengkap")
                    binding.buttonBack.invisible()
                    binding.layoutNominalZakat.gone()
                    binding.layoutFormZakat.gone()
                    binding.layoutPaymentZakat.gone()
                    binding.layoutPaymentProveZakat.gone()
                    binding.layoutSuccessZakat.visible()
                }
            }
            hideKeyboard()
        }
    }

    override fun onImagePicked(base64String: String) {
        vm.transferPhotoBase64.set(base64String)
        binding.imageBukti.loadFromBase64(base64String)
    }

    override fun onBackPressed() {
        when(vm.layoutType.value){
            "default" -> {
                super.onBackPressed()
            }
            "form" -> {
                vm.changeLayout("default")
            }
            "payment" -> {
                vm.changeLayout("form")
            }
            "prove" -> {
                vm.changeLayout("payment")
            }
        }
    }
}