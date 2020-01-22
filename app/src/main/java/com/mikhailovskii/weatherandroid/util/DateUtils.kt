package com.mikhailovskii.weatherandroid.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getDateFromSeconds(seconds: Long): String {
            val simpleDateFormat = SimpleDateFormat("E HH:mm")
            val date = Date(seconds * 1000)

            return simpleDateFormat.format(date)
        }

    }
}