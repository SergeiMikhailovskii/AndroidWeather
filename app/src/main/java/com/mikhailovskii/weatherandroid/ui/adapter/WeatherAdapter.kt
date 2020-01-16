package com.mikhailovskii.weatherandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement
import kotlinx.android.synthetic.main.weather_element.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var weatherList: List<WeatherElement> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(weatherList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(weatherList: List<WeatherElement>) {
        val weatherDiffResult =
            DiffUtil.calculateDiff(WeatherDiffUtilCallback(weatherList, this.weatherList))
        this.weatherList = weatherList
        weatherDiffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(element: WeatherElement) {
            itemView.weather_element_layout.layoutParams.width =
                (itemView.context.resources.displayMetrics.widthPixels * 0.25).toInt()
            itemView.day_tv.text = element.day
            itemView.value_tv.text =
                itemView.resources.getString(R.string.temperature_in_celsius, element.temp)
        }

    }

}