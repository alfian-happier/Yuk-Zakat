package app.alfian.yukzakat.ui.main.view.editprofile

import android.graphics.BitmapFactory
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.usecase.EditProfileUseCase
import app.alfian.yukzakat.databinding.ActivityEditProfileBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.loadFromBase64
import app.alfian.yukzakat.util.toast
import javax.inject.Inject

/**
 * Created by Zharfan on 6/21/2023.
 */

class EditProfileActivity : BaseActivity() {

    @Inject lateinit var vm : EditProfileVM
    private lateinit var binding : ActivityEditProfileBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        binding.buttonProfilePict.setOnClickListener {
            pickImage()
        }
        vm.inputDisplayPict.get().let { currentDisplayPict ->
            if (!currentDisplayPict.isNullOrBlank()){
                binding.profilePict.loadFromBase64(currentDisplayPict)
            }
        }
        vm.editProfileData.observe(this){
            when(it){
                is EditProfileUseCase.EditProfileResult.Success -> {
                    SharedSession.user = it.response
                    changeActivity(HomeActivity())
                }
                is EditProfileUseCase.EditProfileResult.Failure -> {
                    it.error.localizedMessage?.let { error -> toast(error) }
                }
            }
        }
        vm.onBackObservable.observe(this){
            if (it){
                onBackPressed()
            }
        }
    }

    override fun onImagePicked(base64String: String) {
        vm.inputDisplayPict.set(base64String)
        binding.profilePict.loadFromBase64(base64String)
    }
}