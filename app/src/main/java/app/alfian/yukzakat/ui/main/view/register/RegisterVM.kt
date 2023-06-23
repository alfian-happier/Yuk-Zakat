package app.alfian.yukzakat.ui.main.view.register

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.data.repoimpl.UserRepoImpl
import app.alfian.yukzakat.data.usecase.RegisterUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.login.LoginActivity
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import app.alfian.yukzakat.util.goToActivity
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class RegisterVM @Inject constructor(val activity: BaseActivity) : BaseVM(){

    private val userRepo = UserRepoImpl(activity)
    val registerData = MutableLiveData<RegisterUseCase.RegisterResult>()

    val username = ObservableField<String>()
    val fullname = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    fun onRegisterClicked(){
        if (username.get().isNullOrBlank()) {
            activity.toast("Please fill 'username' correctly.")
            return
        }
        if (fullname.get().isNullOrBlank()) {
            activity.toast("Please fill 'nama lengkap' correctly.")
            return
        }
        if (email.get().isNullOrBlank()){
            activity.toast("Please fill 'email' correctly.")
            return
        }
        if (!email.get()!!.contains("@")){
            activity.toast("Invalid 'email address'.")
            return
        }
        if (password.get().isNullOrBlank()){
            activity.toast("Please fill 'password' correctly.")
            return
        }
        if (confirmPassword.get().isNullOrBlank()){
            activity.toast("Please fill 'confirmPassword' correctly.")
            return
        }
        if (password.get()!! != confirmPassword.get()!!){
            activity.toast("Please check your confirmation password.")
            return
        }
        RegisterUseCase(userRepo).execute(username.get()!!, fullname.get()!!, email.get()!!, password.get()!!)
            .compose(applyObservableAsync())
            .subscribe { registerData.postValue(it) }
            .addTo(disposable)
    }

    fun gotoLogin(){
        activity.goToActivity(LoginActivity())
    }
}