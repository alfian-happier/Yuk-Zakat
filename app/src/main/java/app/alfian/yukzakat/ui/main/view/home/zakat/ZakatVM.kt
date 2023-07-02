package app.alfian.yukzakat.ui.main.view.home.zakat

import app.alfian.yukzakat.data.model.ZakatType
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.zakat.ZakatActivity
import app.alfian.yukzakat.util.goToActivity
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

class ZakatVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    fun onZakatPenghasilanClicked(){
        activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_PENGHASILAN))
    }

    fun onZakatFitrahClicked(){
        activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_FITRAH))
    }

    fun onZakatMaalClicked(){
        activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_MAAL))
    }

    fun onShodaqohClicked(){
        activity.goToActivity(ZakatActivity.instance(ZakatType.SHODAQOH))
    }
}