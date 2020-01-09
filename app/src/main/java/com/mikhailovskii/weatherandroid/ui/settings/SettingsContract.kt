package com.mikhailovskii.weatherandroid.ui.settings

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface SettingsContract {

    interface SettingsView : MvpView {

    }

    interface SettingsPresenter : MvpPresenter<SettingsView> {

    }

}