package com.ovchinnikov.anotherweatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



class RestAPI {

    private val weatherApi: OpenWeatherApi

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        weatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun getWeather(ids: IntArray) = weatherApi.getCurrentWeatherByCityIds(ids.joinToString(separator = ","))
}