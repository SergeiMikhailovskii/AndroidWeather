package com.mikhailovskii.weatherandroid.data.api

import com.mikhailovskii.weatherandroid.data.entities.MapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsAPI {
    @GET("/maps/api/geocode/json?sensor=false&key=${API_KEY}")
    suspend fun getLocation(
        @Query("latlng") latlng: String
    ): Response<MapResponse>

    companion object {
        private const val API_KEY = "AIzaSyDxQv6jAbbFOusJYN3rhmtORLbKw12vAnE"
    }

}