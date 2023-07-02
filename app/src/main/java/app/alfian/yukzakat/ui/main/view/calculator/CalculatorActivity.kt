package app.alfian.yukzakat.ui.main.view.calculator

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.databinding.ActivityCalculatorBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.util.addRupiahFormatter
import app.alfian.yukzakat.util.gone
import app.alfian.yukzakat.util.visible
import javax.inject.Inject

/**
 * Created by Alfian on 6/23/2023.
 */

class CalculatorActivity : BaseActivity(){

    @Inject lateinit var vm : CalculatorVM
    private lateinit var binding : ActivityCalculatorBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        binding.inputPendapatanPerBulan.addRupiahFormatter()
        binding.inputBonusTHRDanLainnya.addRupiahFormatter()

        binding.inputUangTunaiTabunganDeposito.addRupiahFormatter()
        binding.inputNilaiEmasPerakPermata.addRupiahFormatter()
        binding.inputKendaraanRumahAset.addRupiahFormatter()
        binding.inputHutangCicilan.addRupiahFormatter()

        vm.layoutType.observe(this){
            when(it){
                "option" -> {
                    binding.layoutOptionCalculator.visible()
                    binding.layoutPenghasilanCalculator.gone()
                    binding.layoutMaalCalculator.gone()
                }
                "penghasilan" -> {
                    binding.layoutOptionCalculator.gone()
                    binding.layoutPenghasilanCalculator.visible()
                    binding.layoutHasilPenghasilan.gone()
                    binding.layoutMaalCalculator.gone()
                }
                "penghasilan_calculated" -> {
                    binding.layoutHasilPenghasilan.visible()
                }
                "maal" -> {
                    binding.layoutOptionCalculator.gone()
                    binding.layoutPenghasilanCalculator.gone()
                    binding.layoutHasilMaal.gone()
                    binding.layoutMaalCalculator.visible()
                }
                "maal_calculated" -> {
                    binding.layoutHasilMaal.visible()
                }
            }
        }

        vm.onBackObservable.observe(this){
            if (it){
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        when(vm.layoutType.value){
            "option" -> {
                super.onBackPressed()
            }
            "penghasilan","penghasilan_calculated" -> {
                vm.changeLayout("option")
            }
            "maal","maal_calculated" -> {
                vm.changeLayout("option")
            }
        }
    }
}