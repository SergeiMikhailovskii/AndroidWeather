package com.mikhailovskii.weatherandroid.data.entities

data class User(

    var login: String? = "",
    var password: String? = "",
    var twitterKey: String? = "",
    var facebookKey: String? = "",
    var googleKey: String? = "",
    var icon: String? = "",
    var preferredUnit: Int? = 0,
    var location: String? = ""

)