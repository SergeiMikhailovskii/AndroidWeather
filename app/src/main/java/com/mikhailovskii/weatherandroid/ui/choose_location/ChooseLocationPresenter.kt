package com.mikhailovskii.weatherandroid.ui.choose_location

import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class ChooseLocationPresenter : ChooseLocationContract.ChooseLocationPresenter,
    BasePresenter<ChooseLocationContract.ChooseLocationView>() {

    override fun saveLocation(location: String) {
        val user = Preference.user
        user?.location = location
        Preference.user = user

        view?.onLocationSaved()
    }

}