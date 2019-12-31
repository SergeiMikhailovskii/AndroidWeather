package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("main")
    @Expose
    var weatherTemp: WeatherTemp? = null,

    @SerializedName("weather")
    @Expose
    var overcast: List<Overcast>? = null,

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

)