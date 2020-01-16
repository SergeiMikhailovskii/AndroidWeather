package com.mikhailovskii.weatherandroid.util

import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import com.mikhailovskii.weatherandroid.data.entities.User

object Preference {

    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(AndroidWeatherApp.appContext)
    private val editor = sharedPreferences.edit()

    private const val PREF_USER = "PREF_USER"

    var user: User?
        get() = Gson().fromJson(sharedPreferences.getString(PREF_USER, null), User::class.java)
        set(user) {
            editor.putString(PREF_USER, Gson().toJson(user)).commit()
        }

}