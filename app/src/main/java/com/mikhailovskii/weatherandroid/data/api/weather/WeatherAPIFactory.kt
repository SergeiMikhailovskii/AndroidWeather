package com.mikhailovskii.weatherandroid.data.api.weather

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WeatherAPIFactory {

    private const val BASE_URL = "http://api.openweathermap.org"

    var apiService: WeatherAPI = createRetrofit()

    private fun createRetrofit(): WeatherAPI {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .build()

            chain.proceed(requestBuilder)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(WeatherAPI::class.java)
    }


}