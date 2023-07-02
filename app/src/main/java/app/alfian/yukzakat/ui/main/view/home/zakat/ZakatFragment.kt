package app.alfian.yukzakat.ui.main.view.home.zakat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.databinding.FragmentZakatBinding
import app.alfian.yukzakat.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class ZakatFragment : BaseFragment() {

    @Inject lateinit var vm : ZakatVM
    private lateinit var binding : FragmentZakatBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zakat, container, false)
        getBaseActivity()?.screenComponent?.inject(this)
        binding.vm = vm
        return binding.root
    }

    override fun observe() {
    }
}