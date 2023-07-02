package app.alfian.yukzakat.ui.main.view.splash

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.databinding.ActivitySplashBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.util.changeActivity
import app.alfian.yukzakat.util.isNotNull
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    @Inject lateinit var vm : SplashVM
    private lateinit var binding: ActivitySplashBinding

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        screenComponent.inject(this)
        binding.vm = vm

    }

    override fun observe() {
        if (SharedSession.user.isNotNull()){
            changeActivity(HomeActivity())
        }
    }
}