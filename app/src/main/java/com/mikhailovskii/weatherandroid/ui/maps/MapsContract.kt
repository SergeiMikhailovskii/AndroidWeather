package com.mikhailovskii.weatherandroid.ui.maps

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface MapsContract {

    interface MapsView : MvpView {

        fun onDataLoaded(result: String)

        fun onLoadingFailed()

        fun onCityFromPreferencesLoaded(response: String?)

        fun onCityFromPreferencesFailed()

    }

    interface MapsPresenter : MvpPresenter<MapsView> {

        fun getDataByLocation(lat: Double, lon: Double)

        fun getCityFromPreferences()

    }

}