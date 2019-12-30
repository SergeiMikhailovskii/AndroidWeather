package com.mikhailovskii.weatherandroid.data.entities.maps

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mikhailovskii.weatherandroid.data.entities.maps.AddressComponent

data class MapResult(

    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent>? = null

)