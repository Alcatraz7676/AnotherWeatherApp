package com.ovchinnikov.anotherweatherapp

import android.app.Application
import com.ovchinnikov.anotherweatherapp.db.WeatherDatabase

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createDB()
    }

    private fun createDB() {
        WeatherDatabase.getInstance().openHelper.writableDatabase
    }
}