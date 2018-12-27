package com.ovchinnikov.anotherweatherapp.api


import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    companion object {
        const val API_KEY = "96487edc4934ac40b7acd64a7f7ac1ad"
    }

    @GET("/data/2.5/group")
    fun getCurrentWeatherByCityIds(@Query("id") ids: String,
                                   @Query("lang") language: String = "ru",
                                   @Query("units") temperatureUnits: String = "metric",
                                   @Query("APPID") apiKey: String = API_KEY): Observable<WeatherListResponse>

    @GET("/data/2.5/weather")
    fun getCurrentWeatherByCityName(@Query("q") name: String,
                                    @Query("lang") language: String = "ru",
                                    @Query("units") temperatureUnits: String = "metric",
                                    @Query("APPID") apiKey: String = API_KEY): Single<WeatherItemResponse>

    @GET("/data/2.5/forecast")
    fun getForecastWeatherByCityId(@Query("id") id: String,
                                    @Query("lang") language: String = "ru",
                                    @Query("units") temperatureUnits: String = "metric",
                                    @Query("APPID") apiKey: String = API_KEY): Single<WeatherListResponse>
}