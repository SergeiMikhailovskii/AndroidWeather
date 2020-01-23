package com.mikhailovskii.weatherandroid.ui.choose_location

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ChooseLocationContract {

    interface ChooseLocationView : MvpView {

    }

    interface ChooseLocationPresenter : MvpPresenter<ChooseLocationView> {

    }

}