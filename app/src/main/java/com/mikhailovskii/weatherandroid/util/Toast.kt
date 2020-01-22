package com.mikhailovskii.weatherandroid.util

import android.widget.Toast
import com.mikhailovskii.weatherandroid.AndroidWeatherApp
import es.dmoral.toasty.Toasty

fun showErrorToast(text: String) {
    Toasty.error(AndroidWeatherApp.appContext, text, Toast.LENGTH_SHORT, true).show()
}

fun showSuccessToast(text: String) {
    Toasty.success(AndroidWeatherApp.appContext, text, Toast.LENGTH_SHORT, true).show()
}

fun showWarningToast(text: String) {
    Toasty.warning(AndroidWeatherApp.appContext, text, Toast.LENGTH_SHORT, true).show()
}