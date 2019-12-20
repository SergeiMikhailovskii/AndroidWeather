package com.mikhailovskii.weatherandroid.ui.ui.base

interface MvpView {

    fun showEmptyState(value: Boolean)

    fun showLoadingIndicator(value: Boolean)

}