package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("dt")
    var date: Long? = null,

    @SerializedName("main")
    var weatherTemp: WeatherTemp? = null,

    @SerializedName("weather")
    var overcast: List<Overcast>? = null,

    @SerializedName("wind")
    var wind: Wind? = null

)