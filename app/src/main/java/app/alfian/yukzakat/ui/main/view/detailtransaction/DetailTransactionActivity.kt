package app.alfian.yukzakat.ui.main.view.detailtransaction

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.databinding.ActivityDetailTransactionBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.util.customviews.ImageViewerDialog
import app.alfian.yukzakat.util.loadFromBase64
import javax.inject.Inject

/**
 * Created by Zharfan on 6/22/2023.
 */

class DetailTransactionActivity : BaseActivity() {

    companion object {
        private var zakat : Zakat? = null

        fun instance(zakat: Zakat) : DetailTransactionActivity {
            Companion.zakat = zakat
            return DetailTransactionActivity()
        }
    }

    @Inject lateinit var vm : DetailTransactionVM
    private lateinit var binding : ActivityDetailTransactionBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_transaction)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        zakat?.let {
            vm.zakatDetail.set(it)
            binding.imageBukti.loadFromBase64(it.transferPhotoBase64)
            binding.imageBukti.setOnClickListener { _ -> ImageViewerDialog.instance(this,it.transferPhotoBase64).show() }

            if (it.anggota.isNotEmpty()){
                val anggotas = StringBuilder()
                it.anggota.forEach { anggota ->
                    anggotas.append("\n${anggota.fullname}")
                }
                vm.fullname.set("${it.fullName}$anggotas")
            } else {
                vm.fullname.set(it.fullName)
            }
        }
        vm.onBackObservable.observe(this){
            if (it){
                onBackPressed()
            }
        }
    }
}