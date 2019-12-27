package com.mikhailovskii.weatherandroid.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MapsAPIFactory private constructor() {

    private val retrofit: Retrofit

    val apiService: MapsAPI
        get() = retrofit.create(MapsAPI::class.java)

    init {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        httpClient.addInterceptor{chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(API_KEY_HEADER, API_KEY_VALUE)
                .build()

            chain.proceed(requestBuilder)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    companion object {

        private const val BASE_URL = "https://maps.googleapis.com"

        private const val API_KEY_HEADER = "key"

        private const val API_KEY_VALUE = "AIzaSyDxQv6jAbbFOusJYN3rhmtORLbKw12vAnE"

    }

}