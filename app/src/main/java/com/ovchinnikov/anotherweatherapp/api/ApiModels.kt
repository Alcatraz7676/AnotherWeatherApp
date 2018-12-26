package com.ovchinnikov.anotherweatherapp.api

import com.squareup.moshi.Json

class WeatherListResponse(val list: List<WeatherItemResponse>)

class WeatherItemResponse(
    val id: Int,
    val name: String,
    @Json(name = "main") val temp: TemperatureResponse,
    val weather: List<WeatherResponse>
)

class TemperatureResponse(
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double
)

class WeatherResponse(
    val id: Int,
    @Json(name = "description") val weatherName: String
)