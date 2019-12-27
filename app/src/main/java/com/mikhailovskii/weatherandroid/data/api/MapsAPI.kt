package com.mikhailovskii.weatherandroid.data.api

import com.mikhailovskii.weatherandroid.data.entities.MapResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MapsAPI {

    @GET("/maps/api/geocode/json?latlng={lat},{lon}&sensor=false&key=${API_KEY}")
    fun getLocation(
        @Path("lat") lat: String,
        @Path("lon") lon: String
    ): Observable<MapResponse>

    companion object {
        private const val API_KEY = "AIzaSyDxQv6jAbbFOusJYN3rhmtORLbKw12vAnE"
    }

}