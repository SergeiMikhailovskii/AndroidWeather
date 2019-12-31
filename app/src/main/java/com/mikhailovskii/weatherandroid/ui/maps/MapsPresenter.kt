package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.api.maps.MapsAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    private val mapsApi = MapsAPIFactory.getInstance().apiService

    override fun getDataByLocation(lat: Double, lon: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mapsApi.getLocation("$lat,$lon")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()

                    val data = "${result?.results?.get(0)?.addressComponents?.get(2)?.shortName}, " +
                            "${result?.results?.get(0)?.addressComponents?.get(5)?.longName}"

                    saveLocationToPreferences(data)

                    view?.onDataLoaded(data)
                } else {
                    view?.onLoadingFailed()
                }
            }
        }
    }

    private fun saveLocationToPreferences(location: String) {
        Preference.getInstance(AndroidWeatherApp.appContext).location = location
    }

}