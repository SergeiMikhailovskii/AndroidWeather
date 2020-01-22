package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.SerializedName

data class Wind(

    @SerializedName("speed")
    var speed: Double? = null,

    @SerializedName("deg")
    var degree: Int? = null

)