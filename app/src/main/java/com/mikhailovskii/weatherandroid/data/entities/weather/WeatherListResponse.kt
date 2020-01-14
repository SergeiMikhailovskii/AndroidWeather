package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.SerializedName

data class WeatherListResponse(

    @SerializedName("list")
    var weatherList: List<WeatherResponse>

)