package com.mikhailovskii.weatherandroid

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.mikhailovskii.weatherandroid.di.AppModules.mvpModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class AndroidWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@AndroidWeatherApp)
            androidFileProperties()
            modules(mvpModule)
        }


    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        lateinit var instance: AndroidWeatherApp
        lateinit var appContext: Context
    }
}