package com.mikhailovskii.weatherandroid.data.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement

class WeatherDiffUtilCallback(
    private val oldList: List<WeatherElement>,
    private val newList: List<WeatherElement>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldWeatherElement = oldList[oldItemPosition]
        val newWeatherElement = newList[newItemPosition]
        return oldWeatherElement.day == newWeatherElement.day
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldWeatherElement = oldList[oldItemPosition]
        val newWeatherElement = newList[newItemPosition]
        return oldWeatherElement.day == newWeatherElement.day
                && oldWeatherElement.temp == newWeatherElement.temp
    }

}