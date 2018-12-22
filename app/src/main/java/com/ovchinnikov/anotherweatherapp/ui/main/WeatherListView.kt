package com.ovchinnikov.anotherweatherapp.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem

@StateStrategyType(AddToEndStrategy::class)
interface WeatherListView : MvpView {
    fun setWeather(weather: List<WeatherItem>)
    @StateStrategyType(SkipStrategy::class)
    fun showSnackbar(errorMessage: String)
}