package app.alfian.yukzakat.ui.main.view.login

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.usecase.LoginUseCase
import app.alfian.yukzakat.databinding.ActivityLoginBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.goToActivity
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class LoginActivity : BaseActivity() {

    @Inject lateinit var vm : LoginVM
    private lateinit var binding : ActivityLoginBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        vm.loginData.observe(this){
            when(it){
                is LoginUseCase.LoginResult.Success -> {
                    SharedSession.user = it.response
                    changeActivity(HomeActivity())
                }
                is LoginUseCase.LoginResult.Failure -> {
                    it.error.localizedMessage?.let { error -> toast(error) }
                }
            }
        }
    }
}