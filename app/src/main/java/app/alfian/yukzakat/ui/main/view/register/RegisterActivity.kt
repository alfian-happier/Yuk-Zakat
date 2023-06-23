package app.alfian.yukzakat.ui.main.view.register

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.usecase.RegisterUseCase
import app.alfian.yukzakat.databinding.ActivityRegisterBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.login.LoginActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class RegisterActivity : BaseActivity(){

    @Inject lateinit var vm : RegisterVM
    private lateinit var binding : ActivityRegisterBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        vm.registerData.observe(this){
            when(it){
                is RegisterUseCase.RegisterResult.Success -> {
                    toast("Success register user, please login to continue.")
                    changeActivity(LoginActivity())
                }
                is RegisterUseCase.RegisterResult.Failure -> {
                    it.error.localizedMessage?.let { error -> toast(error) }
                }
            }
        }
    }
}