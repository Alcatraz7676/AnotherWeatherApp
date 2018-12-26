package com.ovchinnikov.anotherweatherapp.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ovchinnikov.anotherweatherapp.commons.WeatherIcon

@Entity(tableName = "weather")
data class Weather constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "locationName") var locationName: String,
    @ColumnInfo(name = "tempMin") var tempMin: Int,
    @ColumnInfo(name = "tempMax") var tempMax: Int,
    @ColumnInfo(name = "weatherName") var weatherName: String,
    @ColumnInfo(name = "weatherIcon") var weatherIcon: WeatherIcon
)