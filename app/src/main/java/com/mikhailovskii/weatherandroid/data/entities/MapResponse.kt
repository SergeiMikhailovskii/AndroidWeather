package com.mikhailovskii.weatherandroid.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MapResponse(

    @SerializedName("results")
    @Expose
    var results: List<MapResult>? = null

)