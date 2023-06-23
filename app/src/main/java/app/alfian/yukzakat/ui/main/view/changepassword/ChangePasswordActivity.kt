package app.alfian.yukzakat.ui.main.view.changepassword

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.usecase.ChangePasswordUseCase
import app.alfian.yukzakat.databinding.ActivityChangePasswordBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/22/2023.
 */

class ChangePasswordActivity : BaseActivity(){

    @Inject lateinit var vm : ChangePasswordVM
    private lateinit var binding : ActivityChangePasswordBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        vm.changePasswordData.observe(this){
            when(it){
                is ChangePasswordUseCase.ChangePasswordResult.Success -> {
                    toast(it.response)
                    changeActivity(HomeActivity())
                }
                is ChangePasswordUseCase.ChangePasswordResult.Failure -> {
                    it.error.localizedMessage?.let { error -> toast(error) }
                    vm.inputOldPassword.set("")
                    vm.inputNewPassword.set("")
                    vm.inputConfirmNewPassword.set("")
                }
            }
        }

        vm.onBackObservable.observe(this){
            if (it){
                onBackPressed()
            }
        }
    }
}