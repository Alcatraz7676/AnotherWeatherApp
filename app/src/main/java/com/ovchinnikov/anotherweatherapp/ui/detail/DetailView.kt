package com.ovchinnikov.anotherweatherapp.ui.detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ovchinnikov.anotherweatherapp.commons.ForecastItem

@StateStrategyType(AddToEndStrategy::class)
interface DetailView : MvpView {
    fun setLocationName(name: String)
    fun setForecast(forecast: List<ForecastItem>)
    @StateStrategyType(SkipStrategy::class)
    fun showSnackbar(errorMessage: String)
    fun showErrorView()
    fun hideErrorView()
    fun showLoading()
    fun hideLoading()
}