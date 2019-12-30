package com.mikhailovskii.weatherandroid.ui.forecast

import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface ForecastContract {

    interface ForecastView : MvpView {

    }

    interface ForecastPresenter : MvpPresenter<ForecastView> {

    }

}