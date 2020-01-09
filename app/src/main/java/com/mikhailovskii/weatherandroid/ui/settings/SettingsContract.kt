package com.mikhailovskii.weatherandroid.ui.settings

import android.os.Bundle
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface SettingsContract {

    interface SettingsView : MvpView {

        fun onUserDataSaved()

        fun onUserDataFailed()

    }

    interface SettingsPresenter : MvpPresenter<SettingsView> {

        fun saveEditedUserInfo(bundle: Bundle)

    }

}