package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.AndroidWeatherApp
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
            var city = Preference.getInstance(AndroidWeatherApp.appContext).user?.location ?: "Minsk"
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
        val location = Preference.getInstance(AndroidWeatherApp.appContext).user?.location ?: "Minsk"
        view?.onCityFromPreferencesLoaded(location)
    }

    override fun getCityForecast() {
        CoroutineScope(Dispatchers.IO).launch {
            var city = Preference.getInstance(AndroidWeatherApp.appContext).user?.location ?: "Minsk"
            city = city.replace("\\s".toRegex(), "")

            val response = weatherApi.getCityForecast(city)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val list = ArrayList<WeatherElement>()
                    result?.weatherList?.forEach { element ->
                        val temp = element.weatherTemp?.temp?.toInt()?.minus(273)
                        val date = element.date

                        list.add(WeatherElement(day = getDateFromSeconds(date ?: 0), temp = temp))
                    }
                    view?.onWeatherForecastLoaded(list)
                } else {
                    view?.onWeatherForecastFailed()
                }
            }
        }
    }

}