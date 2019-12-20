package com.mikhailovskii.weatherandroid.ui.ui.base

open class BasePresenter<View> : MvpPresenter<View> {

    var view: View? = null

    override fun attachView(view: View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}