package app.alfian.yukzakat.ui.main.view.detailtransaction

import androidx.databinding.ObservableField
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import javax.inject.Inject

/**
 * Created by Alfian on 6/22/2023.
 */

class DetailTransactionVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    val zakatDetail = ObservableField<Zakat>()
    val fullname = ObservableField<String>()
}