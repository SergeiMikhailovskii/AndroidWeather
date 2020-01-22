package com.mikhailovskii.weatherandroid.util

import android.text.format.DateFormat
import java.util.*

class DateUtils {

    companion object {

        fun getDateFromSeconds(seconds: Long): String {
            val date = Date(seconds * 1000)

            return DateFormat.format("E hh:mm", date).toString()
        }

    }
}