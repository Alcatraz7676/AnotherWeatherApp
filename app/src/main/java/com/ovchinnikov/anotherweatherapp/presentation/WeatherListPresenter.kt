package com.ovchinnikov.anotherweatherapp.presentation

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ovchinnikov.anotherweatherapp.repository.DatabaseRepository
import com.ovchinnikov.anotherweatherapp.repository.WeatherRepository
import com.ovchinnikov.anotherweatherapp.ui.main.WeatherListView
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class WeatherListPresenter : MvpPresenter<WeatherListView>() {

    private lateinit var subscriptions: CompositeDisposable
    private val weatherRepository by lazy {
        WeatherRepository(DatabaseRepository())
    }

    fun onCreate() {
        subscriptions = CompositeDisposable()
    }

    fun requestCurrentWeatherList() {

        val subscription = weatherRepository
            .getCurrentWeatherList()
            .subscribe(
                {
                        weatherList ->
                    viewState.apply {
                        setWeather(weatherList)
                        hideLoading()
                    }
                },
                {
                        e ->
                    Log.d( "DebugTag", e.toString())
                    viewState.apply {
                        showSnackbar(e.message ?: "")
                        hideLoading()
                        showErrorView()
                    }
                }
            )
        subscriptions.add(subscription)
    }

    fun requestCurrentWeatherCity(cityName: String) {
        val subscription = weatherRepository
            .getCurrentWeather(cityName)
            .subscribe(
                {
                        weather ->
                    viewState.setWeather(listOf(weather))

                },
                {
                        e ->
                    Log.d( "DebugTag", e.toString())
                    viewState.showSnackbar("Город не найден")
                }
            )
        subscriptions.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
        subscriptions.clear()
    }
}