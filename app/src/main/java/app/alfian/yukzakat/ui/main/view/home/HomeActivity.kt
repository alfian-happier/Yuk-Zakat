package app.alfian.yukzakat.ui.main.view.home

import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.databinding.ActivityHomeBinding
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.main.view.home.dashboard.DashboardFragment
import app.alfian.yukzakat.ui.main.view.home.profile.ProfileFragment
import app.alfian.yukzakat.ui.main.view.home.history.HistoryFragment
import app.alfian.yukzakat.ui.main.view.home.zakat.ZakatFragment
import app.alfian.yukzakat.util.isNotNull
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class HomeActivity : BaseActivity() {

    @Inject lateinit var vm : HomeVM
    private lateinit var binding : ActivityHomeBinding

    private var dashboardFragment : DashboardFragment? = null
    private var zakatFragment : ZakatFragment? = null
    private var historyFragment : HistoryFragment? = null
    private var profileFragment : ProfileFragment? = null

    override fun setup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        screenComponent.inject(this)
        binding.vm = vm
    }

    override fun observe() {
        binding.botnavHome.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_dashboard -> {
                    if (!dashboardFragment.isNotNull())
                        dashboardFragment = DashboardFragment()
                    vm.makeCurrentFragment(dashboardFragment)
                }
                R.id.menu_zakat -> {
                    if (!zakatFragment.isNotNull())
                        zakatFragment = ZakatFragment()
                    vm.makeCurrentFragment(zakatFragment)
                }
                R.id.menu_history -> {
                    if (!historyFragment.isNotNull())
                        historyFragment = HistoryFragment()
                    vm.makeCurrentFragment(historyFragment)
                }
                R.id.menu_profile -> {
                    if (!profileFragment.isNotNull())
                        profileFragment = ProfileFragment()
                    vm.makeCurrentFragment(profileFragment)
                }
            }
            true
        }
        setDashboardFragment()
    }

    private fun setDashboardFragment(){
        dashboardFragment = DashboardFragment()
        vm.makeCurrentFragment(dashboardFragment)
    }
}