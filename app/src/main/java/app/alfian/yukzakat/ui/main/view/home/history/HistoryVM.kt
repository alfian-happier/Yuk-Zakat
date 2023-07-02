package app.alfian.yukzakat.ui.main.view.home.history

import androidx.lifecycle.MutableLiveData
import app.alfian.yukzakat.SharedSession
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.data.repoimpl.ZakatRepoImpl
import app.alfian.yukzakat.data.usecase.GetZakatHistoryUseCase
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.applyObservableAsync
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class HistoryVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    private val zakatRepo = ZakatRepoImpl(activity)
    val getZakatHistoryData = MutableLiveData<GetZakatHistoryUseCase.GetZakatHistoryResult>()

    val itemZakat = arrayListOf<Zakat>()

    fun getZakatHistory(){
        SharedSession.user?.let { user ->
            GetZakatHistoryUseCase(zakatRepo).execute(user)
                .compose(applyObservableAsync())
                .subscribe { getZakatHistoryData.postValue(it) }
                .addTo(disposable)
        }
    }
}