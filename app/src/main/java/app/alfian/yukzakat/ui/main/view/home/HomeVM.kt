package app.alfian.yukzakat.ui.main.view.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import app.alfian.yukzakat.R
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.util.isNotNull
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class HomeVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    fun makeCurrentFragment(fragment: Fragment?) {
        if (fragment.isNotNull()){
            (activity as FragmentActivity).supportFragmentManager.beginTransaction().apply {
                replace(R.id.containerHome, fragment!!)
                commit()
            }
        }
    }
}