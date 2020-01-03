package com.mikhailovskii.weatherandroid.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.mikhailovskii.weatherandroid.data.entities.User

class Preference @SuppressLint("CommitPrefEdits")
private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor


    init {
        editor = sharedPreferences.edit()
    }

    var user: User?
        get() = Gson().fromJson(sharedPreferences.getString(PREF_USER, null), User::class.java)
        set(user) {
            editor.putString(PREF_USER, Gson().toJson(user)).commit()
        }

    var location: String?
        get() = sharedPreferences.getString(PREF_LOCATION, null)
        set(location) {
            editor.putString(PREF_LOCATION, location).commit()
        }

    companion object {

        private const val PREF_USER = "PREF_USER"
        private const val PREF_LOCATION = "PREF_LOCATION"

        private var instance: Preference? = null

        @Synchronized
        fun getInstance(context: Context): Preference {

            if (instance == null) {
                instance = Preference(context)
            }

            return instance!!
        }

    }
}