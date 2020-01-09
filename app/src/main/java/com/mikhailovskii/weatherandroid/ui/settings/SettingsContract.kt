package com.mikhailovskii.weatherandroid.ui.settings

import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface SettingsContract {

    interface SettingsView : MvpView {

        fun onUserDataSaved()

        fun onUserDataFailed()

        fun onInitialUserDataLoaded(user: User?)

        fun onInitialUserDataFailed()

    }

    interface SettingsPresenter : MvpPresenter<SettingsView> {

        fun saveEditedUserInfo(user: User?)

        fun getInitialUserData()

    }

}