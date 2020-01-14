package com.mikhailovskii.weatherandroid.data.api.weather

import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherListResponse
import com.mikhailovskii.weatherandroid.data.entities.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather?")
    suspend fun getCurrentCityWeather(
        @Query("apikey") API_KEY: String,
        @Query("q") city: String
    ): Response<WeatherResponse>

    @GET("/data/2.5/forecast?")
    suspend fun getCityForecast(
        @Query("apikey") API_KEY: String,
        @Query("q") city: String
    ): Response<WeatherListResponse>

}