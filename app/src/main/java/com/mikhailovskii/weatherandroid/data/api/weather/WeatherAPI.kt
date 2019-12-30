package com.mikhailovskii.weatherandroid.data.api.weather

import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {

    @GET("/data/2.5/forecast?q={city}&apikey=$API_KEY")
    suspend fun getCurrentCityWeather(
        @Path("city") city: String
    ): Response<WeatherResponse>


    companion object {
        private const val API_KEY = "8df903ce56f6d18245e72f380beb297d"
    }

}