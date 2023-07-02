package app.alfian.yukzakat.ui.main.view.changepassword

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.data.repoimpl.UserRepoImpl
import app.alfian.yukzakat.data.usecase.ChangePasswordUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Alfian on 6/22/2023.
 */

class ChangePasswordVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    private val userRepo = UserRepoImpl(activity)
    val changePasswordData = MutableLiveData<ChangePasswordUseCase.ChangePasswordResult>()

    val inputOldPassword = ObservableField<String>()
    val inputNewPassword = ObservableField<String>()
    val inputConfirmNewPassword = ObservableField<String>()

    fun save(){
        if (inputOldPassword.get().isNullOrBlank()) {
            activity.toast("Please fill 'Old Password' correctly.")
            return
        }
        if (inputNewPassword.get().isNullOrBlank()) {
            activity.toast("Please fill 'New Password' correctly.")
            return
        }
        if (inputConfirmNewPassword.get().isNullOrBlank()){
            activity.toast("Please fill 'Confirm New Password' correctly.")
            return
        }
        if (inputNewPassword.get()!! != inputConfirmNewPassword.get()!!){
            activity.toast("Please check your confirmation password.")
            return
        }

        ChangePasswordUseCase(userRepo).execute(inputOldPassword.get()!!,inputNewPassword.get()!!)
            .compose(applyObservableAsync())
            .subscribe { changePasswordData.postValue(it) }
            .addTo(disposable)
    }
}