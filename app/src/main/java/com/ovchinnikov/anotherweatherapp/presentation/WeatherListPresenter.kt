package com.ovchinnikov.anotherweatherapp.presentation

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ovchinnikov.anotherweatherapp.commons.ExtensionsUtils
import com.ovchinnikov.anotherweatherapp.repository.WeatherListRepository
import com.ovchinnikov.anotherweatherapp.ui.main.WeatherListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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

        val defaultCities = ExtensionsUtils.DefaultIdsList

        val subscription = weatherRepository.getCurrentWeather(defaultCities)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        weatherList ->
                    viewState.setWeather(weatherList)
                },
                {
                        e ->
                    Log.d( "DebugTag", e.toString())
                    viewState.showSnackbar(e.message ?: "")
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