package com.mikhailovskii.weatherandroid.ui.settings

import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter
import com.mikhailovskii.weatherandroid.util.Preference

class SettingsPresenter : BasePresenter<SettingsContract.SettingsView>(),
    SettingsContract.SettingsPresenter {

    override fun saveEditedUserInfo(user: User?) {
        saveUserDataToPreferences(user)
        view?.onUserDataSaved()
    }

    private fun saveUserDataToPreferences(user: User?) {
        Preference.getInstance(AndroidWeatherApp.appContext).user = user
    }

    private fun saveUserDataToFirebase() {

    }

}