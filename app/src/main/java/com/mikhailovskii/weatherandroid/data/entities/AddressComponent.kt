package com.mikhailovskii.weatherandroid.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddressComponent(

    @SerializedName("long_name")
    @Expose
    var longName: String? = "",

    @SerializedName("short_name")
    @Expose
    var shortName: String? = ""

)