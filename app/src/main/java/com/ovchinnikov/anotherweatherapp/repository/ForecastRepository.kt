package com.ovchinnikov.anotherweatherapp.repository

import com.ovchinnikov.anotherweatherapp.api.RestAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastRepository(private val api: RestAPI = RestAPI()) {

    fun requestForecast(id: Int) = api.getForecast(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}