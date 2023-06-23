package app.alfian.yukzakat.ui.main.view.home.dashboard

import app.alfian.yukzakat.data.model.ZakatType
import app.alfian.yukzakat.ui.base.BaseActivity
import app.alfian.yukzakat.ui.base.BaseVM
import app.alfian.yukzakat.ui.main.view.calculator.CalculatorActivity
import app.alfian.yukzakat.ui.main.view.zakat.ZakatActivity
import app.alfian.yukzakat.util.goToActivity
import javax.inject.Inject

/**
 * Created by Zharfan on 6/13/2023.
 */

class DashboardVM @Inject constructor(val activity: BaseActivity) : BaseVM() {

    fun onCalculatorClicked(){
        activity.goToActivity(CalculatorActivity())
    }

    fun onZakatPenghasilanClicked(){
        activity.goToActivity(ZakatActivity.instance(ZakatType.ZAKAT_PENGHASILAN))
    }
}