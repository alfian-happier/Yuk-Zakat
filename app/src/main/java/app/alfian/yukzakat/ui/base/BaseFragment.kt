package app.alfian.yukzakat.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by Zharfan on 6/13/2023.
 */

abstract class BaseFragment : Fragment() {

    abstract fun observe()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    fun getBaseActivity() : BaseActivity? {
        return if (activity is BaseActivity) activity as BaseActivity? else null
    }
}