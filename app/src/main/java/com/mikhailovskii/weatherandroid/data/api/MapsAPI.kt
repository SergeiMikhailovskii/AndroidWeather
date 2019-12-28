package com.mikhailovskii.weatherandroid.data.api

import com.mikhailovskii.weatherandroid.data.entities.MapResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsAPI {
    @GET("/maps/api/geocode/json?sensor=false&key=${API_KEY}")
    fun getLocation(
        @Query("latlng") latlng: String
    ): Observable<MapResponse>

    companion object {
        private const val API_KEY = "AIzaSyDxQv6jAbbFOusJYN3rhmtORLbKw12vAnE"
    }

}