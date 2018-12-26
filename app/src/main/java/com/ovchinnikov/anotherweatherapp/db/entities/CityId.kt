package com.ovchinnikov.anotherweatherapp.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ids")
data class CityId constructor(
    @PrimaryKey @ColumnInfo(name = "cityId") var cityId: Int
)