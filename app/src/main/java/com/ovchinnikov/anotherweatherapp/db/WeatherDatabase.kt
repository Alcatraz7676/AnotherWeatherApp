package com.ovchinnikov.anotherweatherapp.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.ovchinnikov.anotherweatherapp.App
import com.ovchinnikov.anotherweatherapp.commons.Utils
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

@Database(entities = [CityId::class], version = 1)
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