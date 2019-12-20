package com.mikhailovskii.weatherandroid.ui.ui.base

interface MvpPresenter<View> {

    fun attachView(view: View)

    fun detachView()

}