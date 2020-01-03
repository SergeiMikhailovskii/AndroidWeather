package com.mikhailovskii.weatherandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement
import kotlinx.android.synthetic.main.weather_element.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val weatherList = ArrayList<WeatherElement>()

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
        this.weatherList.clear()
        this.weatherList.addAll(weatherList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(element: WeatherElement) {
            itemView.day_tv.text = element.day
            itemView.value_tv.text = element.temp.toString()
        }

    }

}