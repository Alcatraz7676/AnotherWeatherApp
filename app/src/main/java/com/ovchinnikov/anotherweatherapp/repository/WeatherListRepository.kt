package com.ovchinnikov.anotherweatherapp.repository

import com.ovchinnikov.anotherweatherapp.api.RestAPI
import com.ovchinnikov.anotherweatherapp.commons.ExtensionsUtils
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.commons.getWeatherState
import io.reactivex.Observable

class WeatherListRepository(private val api: RestAPI = RestAPI()) {

    fun getCurrentWeather(defaultCities: IntArray): Observable<List<WeatherItem>> = Observable.create {
        subscriber ->
        val callResponse = api.getWeather(defaultCities)
        val response = callResponse.execute()

        if (response.isSuccessful) {
            val weatherList = response.body()?.list?.map {
                val temperature = it.temp
                val weather = it.weather[0]
                WeatherItem(it.name, temperature.tempMin.toInt(), temperature.tempMax.toInt(), weather.weatherName, weather.id.getWeatherState())
            }
            if (weatherList != null) {
                subscriber.onNext(weatherList)
            }
            subscriber.onComplete()
        } else {
            subscriber.onError(Throwable(response.message()))
        }
    }
}