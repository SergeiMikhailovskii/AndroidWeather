package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.BuildConfig
import com.mikhailovskii.weatherandroid.data.api.weather.WeatherAPIFactory
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import com.mikhailovskii.weatherandroid.util.getDateFromSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastPresenter : BasePresenter<ForecastContract.ForecastView>(),
    ForecastContract.ForecastPresenter {

    private val weatherApi = WeatherAPIFactory.apiService

    override fun getCurrentCityWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            var city =
                Preference.user?.location ?: "Minsk"
            city = city.replace("\\s".toRegex(), "")

            val response = weatherApi.getCurrentCityWeather(BuildConfig.WEATHER_API_KEY, city)
            val result = response.body()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view?.onCurrentCityWeatherLoaded(result)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view?.onCurrentCityWeatherFailed()
                }
            }

        }
    }

    override fun getCityFromPreferences() {
        val location =
            Preference.user?.location ?: "Minsk"
        view?.onCityFromPreferencesLoaded(location)
    }

    override fun getCityForecast() {
        CoroutineScope(Dispatchers.IO).launch {
            var city =
                Preference.user?.location ?: "Minsk"
            city = city.replace("\\s".toRegex(), "")

            val response = weatherApi.getCityForecast(BuildConfig.WEATHER_API_KEY, city)
            val result = response.body()

            if (response.isSuccessful) {
                val list = ArrayList<WeatherElement>()
                result?.weatherList?.forEach { element ->
                    val temp = element.weatherTemp?.temp?.toInt()?.minus(273)
                    val date = element.date

                    list.add(WeatherElement(day = getDateFromSeconds(date ?: 0), temp = temp))
                }
                withContext(Dispatchers.Main) {
                    view?.onWeatherForecastLoaded(list)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view?.onWeatherForecastFailed()
                }
            }

        }
    }

}