package app.alfian.yukzakat.di

import app.alfian.yukzakat.YukZakatApplication
import app.alfian.yukzakat.di.module.ApplicationModule
import app.alfian.yukzakat.di.module.RepositoryModule
import app.alfian.yukzakat.di.module.ScreenModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Alfian on 6/13/2023.
 */

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun inject(app : YukZakatApplication)

    fun plus(screenModule: ScreenModule): ScreenComponent
}