package com.mikhailovskii.weatherandroid.ui.login

import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.ui.base.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private lateinit var database: DatabaseReference

    override fun saveUserData(bundle: Bundle) {
        database = FirebaseDatabase.getInstance().reference

        val login = bundle.getString("login")
        val password = bundle.getString("password")

        val user = User(login = login, password = password)

        database.child("users").push().setValue(user)

        view?.onLoggedIn()

    }

}