package com.ovchinnikov.anotherweatherapp.repository

import com.ovchinnikov.anotherweatherapp.api.RestAPI
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.db.entities.CityId
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class WeatherRepository(private val databaseRepository: DatabaseRepository, private val api: RestAPI = RestAPI(databaseRepository)) {

    private fun requestWeatherList(cityList: List<CityId>) = api.getWeatherList(cityList)

    private fun requestWeather(cityName: String) = api.getWeather(cityName)

    fun getCurrentWeatherList(): Single<ArrayList<WeatherItem>>
            = databaseRepository.getIds()
            .flatMapObservable { ids -> Observable.fromIterable(ids).buffer(20,20) }
            .flatMap { ids -> requestWeatherList(ids) }
            .collectInto(ArrayList<WeatherItem>(), { list, elem -> list.addAll(elem)})
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getCurrentWeather(cityName: String): Single<WeatherItem> = requestWeather(cityName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}