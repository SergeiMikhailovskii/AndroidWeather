package com.mikhailovskii.weatherandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherElement
import com.mikhailovskii.weatherandroid.util.displayMetrics
import kotlinx.android.synthetic.main.weather_element.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer<WeatherElement>(this, WeatherDiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(weatherList: List<WeatherElement>) {
        differ.submitList(weatherList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(element: WeatherElement) {
            itemView.weather_element_layout.layoutParams.width =
                (itemView.displayMetrics.widthPixels * QUARTER_SCREEN).toInt()
            itemView.day_tv.text = element.day
            itemView.value_tv.text =
                itemView.resources.getString(R.string.temperature_in_celsius, element.temp)
        }

    }

    companion object {
        private const val QUARTER_SCREEN = 0.25
    }

}