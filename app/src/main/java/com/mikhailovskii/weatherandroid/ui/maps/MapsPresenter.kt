package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class MapsPresenter : BasePresenter<MapsContract.MapsView>(), MapsContract.MapsPresenter {

    override fun getCityFromPreferences() {
        val location = Preference.user?.location
        view?.onCityFromPreferencesLoaded(location)
    }

    override fun saveLocationToPreferences(location: String) {
        val user = Preference.user
        user?.location = location
        Preference.user = user
    }

}