package com.ovchinnikov.anotherweatherapp.commons

import com.ovchinnikov.anotherweatherapp.commons.adapter.AdapterConstants
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType

data class WeatherItem(
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