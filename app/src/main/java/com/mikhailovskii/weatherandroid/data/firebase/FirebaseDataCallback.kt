package com.mikhailovskii.weatherandroid.data.firebase

interface FirebaseDataCallback<T> {

    fun onFirebaseDataLoaded(data: T)

    fun onFirebaseDataFailed()

}