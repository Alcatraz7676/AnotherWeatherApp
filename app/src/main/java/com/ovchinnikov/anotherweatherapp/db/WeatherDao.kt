package com.ovchinnikov.anotherweatherapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe


@Dao interface WeatherDao {

    @Query("SELECT * FROM Weather") fun getIds(): Maybe<List<CityId>>

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertIds(vararg ids: CityId): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertId(id: CityId): Long

}