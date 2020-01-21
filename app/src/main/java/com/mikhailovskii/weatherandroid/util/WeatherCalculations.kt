package com.mikhailovskii.weatherandroid.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun getWindDirection(degree: Int): String {
    return when {
        degree > 337.5 -> "N"
        degree > 292.5 -> "NW"
        degree > 247.5 -> "W"
        degree > 202.5 -> "SW"
        degree > 157.5 -> "S"
        degree > 122.5 -> "SE"
        degree > 67.5 -> "E"
        degree > 22.5 -> "NE"
        else -> "N"
    }
}

@SuppressLint("SimpleDateFormat")
fun getDateFromSeconds(seconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("E HH:mm")
    val date = Date(seconds * 1000)

    return simpleDateFormat.format(date)
}

fun convertKelvinToCelsius(kelvin: Double): Double = kelvin - DIFFERENCE_KELVIN_CELSIUS