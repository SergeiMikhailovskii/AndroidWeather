package com.mikhailovskii.weatherandroid.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDateFromSeconds(seconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("E HH:mm")
    val date = Date(seconds * 1000)

    return simpleDateFormat.format(date)
}

class WeatherUtils {

    companion object {
        private const val KELVIN_TO_CELSIUS_COEFFICIENT = 273

        fun convertKelvinToCelsius(kelvin: Double): Double = kelvin - KELVIN_TO_CELSIUS_COEFFICIENT

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

    }
}
