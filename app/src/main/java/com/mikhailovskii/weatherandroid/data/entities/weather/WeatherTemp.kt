package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.SerializedName

data class WeatherTemp (

    @SerializedName("temp")
    var temp: Double? = null,

    @SerializedName("feels_like")
    var feelsLike: Double? = null,

    @SerializedName("pressure")
    var pressure: Int? = null,

    @SerializedName("humidity")
    var humidity: Int? = null

)