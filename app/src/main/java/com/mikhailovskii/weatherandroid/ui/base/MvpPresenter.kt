package com.mikhailovskii.weatherandroid.ui.base

interface MvpPresenter<View> {

    fun attachView(view: View)

    fun detachView()

}