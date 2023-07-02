package app.alfian.yukzakat.ui.main.view.home.profile

import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.changepassword.ChangePasswordActivity
import app.alfian.yukzakat.ui.main.view.editprofile.EditProfileActivity
import app.alfian.yukzakat.ui.main.view.splash.SplashActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.goToActivity
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class ProfileVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    fun onEditProfileClicked(){
        activity.goToActivity(EditProfileActivity())
    }

    fun onChangePasswordClicked(){
        activity.goToActivity(ChangePasswordActivity())
    }

    fun onLogoutClicked(){
        SharedSession.reset()
        activity.changeActivity(SplashActivity())
    }
}