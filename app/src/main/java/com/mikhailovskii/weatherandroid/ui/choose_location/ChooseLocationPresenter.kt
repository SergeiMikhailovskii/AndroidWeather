package com.mikhailovskii.weatherandroid.ui.choose_location

import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.data.firebase.FirebaseModel
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class ChooseLocationPresenter : ChooseLocationContract.ChooseLocationPresenter,
    BasePresenter<ChooseLocationContract.ChooseLocationView>() {

    override fun saveLocation(location: String) {
        val user = Preference.user
        user?.location = location
        Preference.user = user

        saveLocationInFirebase(user)

        view?.onLocationSaved()
    }

    private fun saveLocationInFirebase(user: User?) {
        FirebaseModel().saveUserLocation(user)
    }

}