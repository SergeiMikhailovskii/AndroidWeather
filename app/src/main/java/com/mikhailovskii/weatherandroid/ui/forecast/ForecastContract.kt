package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ForecastContract {

    interface ForecastView : MvpView {

        fun onCurrentCityWeatherLoaded()

        fun onCurrentCityWeatherFailed()

    }

    interface ForecastPresenter : MvpPresenter<ForecastView> {

        fun getCurrentCityWeather(city: String)

    }

}

// key 8df903ce56f6d18245e72f380beb297d