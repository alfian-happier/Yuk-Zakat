package app.alfian.yukzakat.di.module

import app.alfian.yukzakat.di.PerScreen
import app.alfian.yukzakat.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Zharfan on 6/13/2023.
 */

@Module
class ScreenModule(private val activity: BaseActivity) {

    @PerScreen
    @Provides
    fun providesActivity(): BaseActivity {
        return activity
    }
}