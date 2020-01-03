package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.api.weather.WeatherAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastPresenter : BasePresenter<ForecastContract.ForecastView>(),
    ForecastContract.ForecastPresenter {

    private val weatherApi = WeatherAPIFactory.getInstance().apiService

    override fun getCurrentCityWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            var city = Preference.getInstance(AndroidWeatherApp.appContext).location ?: "Minsk"
            city = city.replace("\\s".toRegex(), "")

            val response = weatherApi.getCurrentCityWeather(city)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()

                    view?.onCurrentCityWeatherLoaded(result)
                }
            }
        }
    }

    override fun getCityFromPreferences() {
        val location = Preference.getInstance(AndroidWeatherApp.appContext).location
        view?.onCityFromPreferencesLoaded(location)
    }

}