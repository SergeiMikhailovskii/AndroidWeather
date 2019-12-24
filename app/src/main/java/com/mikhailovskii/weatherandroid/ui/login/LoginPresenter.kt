package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private lateinit var database: DatabaseReference

    override fun saveUserData(bundle: Bundle) {
        database = FirebaseDatabase.getInstance().reference
        view?.onLoggedIn()
    }

}