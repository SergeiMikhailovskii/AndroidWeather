package com.mikhailovskii.weatherandroid.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MapResult(

    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent>? = null

)