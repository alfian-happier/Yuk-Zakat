package app.alfian.yukzakat

import android.app.Application
import app.alfian.yukzakat.di.ApplicationComponent
import app.alfian.yukzakat.di.DaggerApplicationComponent
import app.alfian.yukzakat.di.module.ApplicationModule
import app.alfian.yukzakat.di.module.RepositoryModule

/**
 * Created by Alfian on 6/13/2023.
 */

class YukZakatApplication : Application() {

    companion object {
        lateinit var instance : YukZakatApplication private set
    }

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedSession.init(this)
        inject()
    }

    private fun inject() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .repositoryModule(RepositoryModule())
            .build()
        component.inject(this)
    }
}