package app.alfian.yukzakat.ui.main.view.editprofile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.repoimpl.UserRepoImpl
import app.alfian.yukzakat.data.usecase.EditProfileUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import app.alfian.yukzakat.util.isNotNull
import javax.inject.Inject

/**
 * Created by Alfian on 6/21/2023.
 */

class EditProfileVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    private val userRepo = UserRepoImpl(activity)
    val editProfileData = MutableLiveData<EditProfileUseCase.EditProfileResult>()

    val inputFullName = ObservableField<String>()
    val inputEmail = ObservableField<String>()
    val inputDisplayPict = ObservableField<String>()

    init {
        inputFullName.set(SharedSession.user?.fullname)
        inputEmail.set(SharedSession.user?.email)
        inputDisplayPict.set(SharedSession.user?.displayPictBase64)
    }

    fun save(){
        if (inputFullName.get().isNullOrBlank() || inputEmail.get().isNullOrBlank() || !inputDisplayPict.get().isNotNull()) {
            return
        }
        EditProfileUseCase(userRepo).execute(inputFullName.get()!!,inputEmail.get()!!,inputDisplayPict.get()!!)
            .compose(applyObservableAsync())
            .subscribe { editProfileData.postValue(it) }
            .addTo(disposable)
    }
}