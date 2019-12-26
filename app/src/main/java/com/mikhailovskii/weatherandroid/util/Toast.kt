package com.mikhailovskii.weatherandroid.util

import android.widget.Toast
import com.mikhailovskii.weatherandroid.AndroidWeatherApp

fun toast(text: String) {
    Toast.makeText(AndroidWeatherApp.appContext, text, Toast.LENGTH_SHORT).show()
}