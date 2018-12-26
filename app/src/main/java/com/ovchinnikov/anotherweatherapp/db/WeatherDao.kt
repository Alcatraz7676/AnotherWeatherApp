package com.ovchinnikov.anotherweatherapp.db

import android.arch.persistence.room.*
import com.ovchinnikov.anotherweatherapp.db.entities.CityId
import com.ovchinnikov.anotherweatherapp.db.entities.Weather
import io.reactivex.Maybe


@Dao interface WeatherDao {

    @Query("SELECT * FROM Ids") fun getIds(): Maybe<List<CityId>>

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertIds(vararg ids: CityId): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertId(id: CityId): Long

//    @Query("SELECT * FROM Weather") fun getWeather(): Maybe<List<Weather>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertWeathers(vararg weathers: Weather): List<Long>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertWeather(weather: Weather): Long
//
//    @Update fun updateWeather(vararg weathers: Weather): Int

}