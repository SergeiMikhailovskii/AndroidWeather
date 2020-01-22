package com.mikhailovskii.weatherandroid.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement

class WeatherDiffUtilCallback : DiffUtil.ItemCallback<WeatherElement>() {

    override fun areItemsTheSame(oldItem: WeatherElement, newItem: WeatherElement): Boolean = true

    override fun areContentsTheSame(oldItem: WeatherElement, newItem: WeatherElement): Boolean {
        return oldItem.day == newItem.day
                && oldItem.temp == newItem.temp
    }

}