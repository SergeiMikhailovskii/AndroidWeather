package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.data.api.weather.WeatherAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastPresenter : BasePresenter<ForecastContract.ForecastView>(), ForecastContract.ForecastPresenter {

    val weatherApi = WeatherAPIFactory.getInstance().apiService

    override fun getCurrentCityWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.getCurrentCityWeather(city)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()

                    val data = result?.weatherTemp?.temp!!



                }
            }
        }
    }

}