package com.mikhailovskii.weatherandroid.ui.base

interface MvpView {

    fun showEmptyState(value: Boolean) {}

    fun showLoadingIndicator(value: Boolean) {}

}