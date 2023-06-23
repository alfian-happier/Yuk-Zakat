package app.alfian.yukzakat.ui.main.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.data.repoimpl.UserRepoImpl
import app.alfian.yukzakat.data.usecase.LoginUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.register.RegisterActivity
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import app.alfian.yukzakat.util.goToActivity
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class LoginVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    private val userRepo = UserRepoImpl(activity)
    val loginData = MutableLiveData<LoginUseCase.LoginResult>()

    val username = ObservableField<String>()
    val password = ObservableField<String>()

    fun onLoginClicked(){
        if (username.get().isNullOrBlank()){
            activity.toast("Please fill 'username' correctly.")
            return
        }
        if (password.get().isNullOrBlank()){
            activity.toast("Please fill 'password' correctly.")
            return
        }
        LoginUseCase(userRepo).execute(username.get()!!, password.get()!!)
            .compose(applyObservableAsync())
            .subscribe { loginData.postValue(it) }
            .addTo(disposable)
    }

    fun gotoRegister(){
        activity.goToActivity(RegisterActivity())
    }
}