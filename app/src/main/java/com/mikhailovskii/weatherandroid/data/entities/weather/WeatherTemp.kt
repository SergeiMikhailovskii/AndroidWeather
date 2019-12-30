package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherTemp (

    @SerializedName("temp")
    @Expose
    var temp: Double? = null,

    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double? = null,

    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null,

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null

)