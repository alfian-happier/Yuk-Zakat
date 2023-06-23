package app.alfian.yukzakat.di.module

import android.content.Context
import app.alfian.yukzakat.YukZakatApplication
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by Zharfan on 6/13/2023.
 */

@Module
class ApplicationModule(private val application: YukZakatApplication) {

    @Provides
    @Singleton
    fun provideApplication() : YukZakatApplication {
        return application
    }

    @Provides
    fun provideContext() : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDisposable() : CompositeDisposable {
        return CompositeDisposable()
    }
}