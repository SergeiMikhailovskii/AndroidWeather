package com.mikhailovskii.weatherandroid.data.entities.weather

import com.google.gson.annotations.SerializedName

data class Overcast (

    @SerializedName("main")
    var mainInfo: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("icon")
    var icon: String? = null

)