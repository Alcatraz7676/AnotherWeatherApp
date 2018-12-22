package com.ovchinnikov.anotherweatherapp.api

import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.commons.getWeatherState
import com.ovchinnikov.anotherweatherapp.db.CityId
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        weatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun getWeather(cityList: List<CityId>): Single<List<WeatherItem>> =
        weatherApi.getCurrentWeatherByCityIds(cityList.map { it -> it.cityId }.joinToString(separator = ","))
            .map { weatherListResponse ->
                weatherListResponse.list.map {
                    val temperature = it.temp
                    val weather = it.weather[0]
                    WeatherItem(it.name, temperature.tempMin.toInt(), temperature.tempMax.toInt(), weather.weatherName, weather.id.getWeatherState())
                    }
            }
}