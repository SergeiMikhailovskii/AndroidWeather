package com.mikhailovskii.weatherandroid.ui.choose_location

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ChooseLocationContract {

    interface ChooseLocationView : MvpView {

        fun onLocationSaved()

        fun onLocationFailed()

    }

    interface ChooseLocationPresenter : MvpPresenter<ChooseLocationView> {

        fun saveLocation(location: String)

    }

}