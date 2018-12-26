package com.ovchinnikov.anotherweatherapp.repository

import com.ovchinnikov.anotherweatherapp.db.WeatherDatabase
import com.ovchinnikov.anotherweatherapp.db.entities.CityId
import io.reactivex.Maybe

class DatabaseRepository(private val instance: WeatherDatabase = WeatherDatabase.getInstance()) {

    fun getIds(): Maybe<List<CityId>> {
        return instance.weatherDao().getIds()
    }

    fun insertId(id: Int): Long {
        val cityId = CityId(id)
        return instance.weatherDao().insertId(cityId)
    }
}