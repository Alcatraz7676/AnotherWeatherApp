package com.ovchinnikov.anotherweatherapp.repository

import com.ovchinnikov.anotherweatherapp.api.RestAPI
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.db.CityId
import com.ovchinnikov.anotherweatherapp.db.WeatherDatabase
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherListRepository(private val api: RestAPI = RestAPI()) {

    private fun requestWeather(cityList: List<CityId>) = api.getWeather(cityList)

    fun getCurrentWeather(): Maybe<List<WeatherItem>> = WeatherDatabase.getInstance()
        .weatherDao().getIds()
        .flatMapSingleElement { list -> requestWeather(list) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}