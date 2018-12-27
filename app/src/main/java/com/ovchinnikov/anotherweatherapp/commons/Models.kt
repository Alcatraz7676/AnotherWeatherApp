package com.ovchinnikov.anotherweatherapp.commons

import com.ovchinnikov.anotherweatherapp.commons.adapter.AdapterConstants
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType
import java.util.*

data class WeatherItem(
    val id: Int,
    val locationName: String,
    val tempMin: Int,
    val tempMax: Int,
    val weatherName: String,
    val weatherIcon: WeatherIcon
) : ViewType {
    override fun getViewType() = AdapterConstants.WEATHER
}

enum class WeatherIcon {
    CLEAR,
    CLOUDY,
    FOG,
    LIGHT_CLOUDS,
    LIGHT_RAIN,
    RAIN,
    SNOW,
    STORM,
    NONE
}

data class ForecastItem(
    val date: Calendar,
    val tempMin: Int,
    val tempMax: Int,
    val weatherIcon: WeatherIcon
)