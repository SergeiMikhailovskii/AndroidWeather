package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.data.api.maps.MapsAPIFactory
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    val mapsApi = MapsAPIFactory.getInstance().apiService

    override fun getDataByLocation(lat: Double, lon: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mapsApi.getLocation("$lat,$lon")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()

                    val data = "\uD83D\uDCCD ${result?.results?.get(0)?.addressComponents?.get(2)?.shortName}, " +
                            "${result?.results?.get(0)?.addressComponents?.get(5)?.longName}"

                    view?.onDataLoaded(data)
                } else {
                    view?.onLoadingFailed()
                }
            }
        }
    }

}