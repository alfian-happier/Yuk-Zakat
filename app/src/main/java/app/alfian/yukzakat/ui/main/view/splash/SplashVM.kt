package app.alfian.yukzakat.ui.main.view.splash

import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.login.LoginActivity
import app.alfian.yukzakat.ui.main.view.register.RegisterActivity
import app.alfian.yukzakat.util.goToActivity
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class SplashVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    fun onLoginClicked(){
        activity.goToActivity(LoginActivity())
    }

    fun onRegisterClicked(){
        activity.goToActivity(RegisterActivity())
    }
}