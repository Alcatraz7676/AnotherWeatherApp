package com.ovchinnikov.anotherweatherapp.presentation

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ovchinnikov.anotherweatherapp.repository.WeatherListRepository
import com.ovchinnikov.anotherweatherapp.ui.main.WeatherListView
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class WeatherListPresenter : MvpPresenter<WeatherListView>() {

    private lateinit var subscriptions: CompositeDisposable
    private val weatherRepository by lazy {
        WeatherListRepository()
    }

    fun onCreate() {
        subscriptions = CompositeDisposable()
    }

    fun requestCurrentWeather() {

        val subscription = weatherRepository
            .getCurrentWeather()
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

    override fun onDestroy() {
        super.onDestroy()
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
        subscriptions.clear()
    }
}