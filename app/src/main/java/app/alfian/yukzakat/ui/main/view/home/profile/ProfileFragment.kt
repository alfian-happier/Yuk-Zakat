package app.alfian.yukzakat.ui.main.view.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.databinding.FragmentProfileBinding
import app.alfian.yukzakat.ui.base.BaseFragment
import app.alfian.yukzakat.util.customviews.ImageViewerDialog
import app.alfian.yukzakat.util.loadFromBase64
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class ProfileFragment : BaseFragment() {

    @Inject lateinit var vm : ProfileVM
    lateinit var binding : FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        getBaseActivity()?.screenComponent?.inject(this)
        binding.vm = vm
        return binding.root
    }

    override fun observe() {
        SharedSession.user?.displayPictBase64?.let { currentDisplayPict ->
            if (currentDisplayPict.isNotBlank()){
                binding.profilePict.loadFromBase64(currentDisplayPict)
                binding.profilePict.setOnClickListener { ImageViewerDialog.instance(requireContext(),currentDisplayPict).show() }
            }
        }
    }
}