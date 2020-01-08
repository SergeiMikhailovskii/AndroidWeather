package com.mikhailovskii.weatherandroid.ui.forecast

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement

class XAxisFormatter(var weatherList: List<WeatherElement>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return weatherList[value.toInt()].day ?: ""
    }

}