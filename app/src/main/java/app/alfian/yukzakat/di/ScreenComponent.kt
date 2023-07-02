package app.alfian.yukzakat.di

import app.alfian.yukzakat.di.module.ScreenModule
import app.alfian.yukzakat.ui.main.view.calculator.CalculatorActivity
import app.alfian.yukzakat.ui.main.view.changepassword.ChangePasswordActivity
import app.alfian.yukzakat.ui.main.view.detailtransaction.DetailTransactionActivity
import app.alfian.yukzakat.ui.main.view.editprofile.EditProfileActivity
import app.alfian.yukzakat.ui.main.view.home.HomeActivity
import app.alfian.yukzakat.ui.main.view.home.dashboard.DashboardFragment
import app.alfian.yukzakat.ui.main.view.home.profile.ProfileFragment
import app.alfian.yukzakat.ui.main.view.home.history.HistoryFragment
import app.alfian.yukzakat.ui.main.view.home.zakat.ZakatFragment
import app.alfian.yukzakat.ui.main.view.login.LoginActivity
import app.alfian.yukzakat.ui.main.view.register.RegisterActivity
import app.alfian.yukzakat.ui.main.view.splash.SplashActivity
import app.alfian.yukzakat.ui.main.view.zakat.ZakatActivity
import dagger.Subcomponent

/**
 * Created by Alfian on 6/13/2023.
 */

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(editProfileActivity: EditProfileActivity)
    fun inject(changePasswordActivity: ChangePasswordActivity)

    fun inject(dashboardFragment: DashboardFragment)
    fun inject(zakatFragment: ZakatFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(profileFragment: ProfileFragment)

    fun inject(zakatActivity: ZakatActivity)
    fun inject(detailTransactionActivity: DetailTransactionActivity)
    fun inject(calculatorActivity: CalculatorActivity)
}