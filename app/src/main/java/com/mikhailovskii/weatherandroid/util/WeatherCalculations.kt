package com.mikhailovskii.weatherandroid.util

fun getWindDirection(degree: Int): String {
    when {
        degree > 337.5 -> {
            return "N"
        }
        degree > 292.5 -> {
            return "NW"
        }
        degree > 247.5 -> {
            return "W"
        }
        degree > 202.5 -> {
            return "SW"
        }
        degree > 157.5 -> {
            return "S"
        }
        degree > 122.5 -> {
            return "SE"
        }
        degree > 67.5 -> {
            return "E"
        }
        degree > 22.5 -> {
            return "NE"
        }
        else -> {
            return "N"
        }
    }
}