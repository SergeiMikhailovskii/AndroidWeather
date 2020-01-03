package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherListResponse (

    @SerializedName("list")
    @Expose
    var weatherList: List<WeatherResponse>

)