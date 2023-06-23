package app.alfian.yukzakat.ui.main.view.home.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.usecase.GetZakatHistoryUseCase
import app.alfian.yukzakat.databinding.FragmentHistoryBinding
import app.alfian.yukzakat.ui.base.BaseFragment
import app.alfian.yukzakat.ui.main.view.detailtransaction.DetailTransactionActivity
import app.alfian.yukzakat.ui.main.view.home.history.adapter.HistoryListAdapter
import app.alfian.yukzakat.util.goToActivity
import app.alfian.yukzakat.util.isNotNull
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class HistoryFragment : BaseFragment() {

    @Inject lateinit var vm : HistoryVM
    private lateinit var binding : FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        getBaseActivity()?.screenComponent?.inject(this)
        binding.vm = vm
        return binding.root
    }

    override fun observe() {
        vm.getZakatHistoryData.observe(this){
            when(it){
                is GetZakatHistoryUseCase.GetZakatHistoryResult.Success -> {
                    vm.itemZakat.clear()
                    vm.itemZakat.addAll(it.response)
                    setRecycler()
                }
                is GetZakatHistoryUseCase.GetZakatHistoryResult.Failure -> {
                    it.error.printStackTrace()
                }
            }
        }
        binding.swipeLayout.setOnRefreshListener {
            refresh()
        }
        refresh()
    }

    private fun refresh(){
        binding.swipeLayout.isRefreshing = true
        vm.getZakatHistory()
    }

    private fun setRecycler(){
        binding.recyclerHistory.layoutManager = LinearLayoutManager(getBaseActivity())
        binding.recyclerHistory.adapter = HistoryListAdapter(vm.itemZakat){ zakat, _ ->
            if (zakat.isNotNull())
                getBaseActivity()?.goToActivity(DetailTransactionActivity.instance(zakat!!))
        }
        binding.swipeLayout.isRefreshing = false
    }
}