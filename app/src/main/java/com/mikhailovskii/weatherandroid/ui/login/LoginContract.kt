package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import com.mikhailovskii.weatherandroid.ui.base.MvpPresenter
import com.mikhailovskii.weatherandroid.ui.base.MvpView

interface LoginContract {

    interface LoginView : MvpView {

        fun onLoggedIn()

        fun onLoginFailed()

    }

    interface LoginPresenter : MvpPresenter<LoginView> {

        fun saveUserData(bundle: Bundle)

    }

}