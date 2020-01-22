package com.mikhailovskii.weatherandroid.util

import android.util.DisplayMetrics
import android.view.View

val View.displayMetrics: DisplayMetrics
    get() = resources.displayMetrics