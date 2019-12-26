package com.mikhailovskii.weatherandroid

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class AndroidWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()

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