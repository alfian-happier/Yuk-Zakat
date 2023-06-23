package app.alfian.yukzakat.ui.main.view.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.databinding.FragmentDashboardBinding
import app.alfian.yukzakat.ui.base.BaseFragment
import app.alfian.yukzakat.util.loadFromBase64
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class DashboardFragment : BaseFragment() {

    @Inject lateinit var vm : DashboardVM
    lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        getBaseActivity()?.screenComponent?.inject(this)
        binding.vm = vm
        return binding.root
    }

    override fun observe() {
        SharedSession.user?.displayPictBase64?.let { profilePict ->
            binding.profilePict.loadFromBase64(profilePict)
        }
    }
}