package com.ovchinnikov.anotherweatherapp.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.content.Context
import android.util.Log
import com.ovchinnikov.anotherweatherapp.App
import com.ovchinnikov.anotherweatherapp.commons.Utils
import com.ovchinnikov.anotherweatherapp.db.converters.WeatherIconConverter
import com.ovchinnikov.anotherweatherapp.db.entities.CityId
import com.ovchinnikov.anotherweatherapp.db.entities.Weather
import java.util.concurrent.Executors

@Database(entities = [CityId::class, Weather::class], version = 1)
@TypeConverters(WeatherIconConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private var INSTANCE: WeatherDatabase? = null

        private val lock = Any()

        fun getInstance(): WeatherDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase()
                }
                return INSTANCE!!
            }
        }

        private fun buildDatabase(context: Context = App.instance): WeatherDatabase {
            return Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "Weather.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            val ids = getInstance()
                                .weatherDao().insertIds(*Utils.DefaultIdsList)
                            Log.d("DebugTag", ids.toString())
                        }
                    }
                })
                .build()
        }
    }

}