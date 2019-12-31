package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherResponse
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ForecastContract {

    interface ForecastView : MvpView {

        fun onCurrentCityWeatherLoaded(response: WeatherResponse?)

        fun onCurrentCityWeatherFailed()

    }

    interface ForecastPresenter : MvpPresenter<ForecastView> {

        fun getCurrentCityWeather(city: String)

    }

}
