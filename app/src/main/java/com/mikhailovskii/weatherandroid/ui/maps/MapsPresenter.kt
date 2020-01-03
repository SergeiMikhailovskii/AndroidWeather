package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    override fun getCityFromPreferences() {
        val location = Preference.getInstance(AndroidWeatherApp.appContext).location
        view?.onCityFromPreferencesLoaded(location)
    }

    override fun saveLocationToPreferences(location: String) {
        Preference.getInstance(AndroidWeatherApp.appContext).location = location
    }

}