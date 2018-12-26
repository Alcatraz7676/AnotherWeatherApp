package com.ovchinnikov.anotherweatherapp.db.converters

import android.arch.persistence.room.TypeConverter
import com.ovchinnikov.anotherweatherapp.commons.WeatherIcon

class WeatherIconConverter {

    @TypeConverter
    fun restoreEnum(enumName: String) = WeatherIcon.valueOf(enumName)

    @TypeConverter
    fun saveEnumToString(enumType: WeatherIcon) = enumType.name
}