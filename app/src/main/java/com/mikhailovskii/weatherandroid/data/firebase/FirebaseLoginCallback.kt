package com.mikhailovskii.weatherandroid.data.firebase

interface FirebaseLoginCallback {

    fun onLoggedInWithFilledInfo()

    fun onLoggedInWithEmptyLocation()

    fun onLoginFailed()

}