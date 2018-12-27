package com.ovchinnikov.anotherweatherapp.presentation

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ovchinnikov.anotherweatherapp.repository.ForecastRepository
import com.ovchinnikov.anotherweatherapp.ui.detail.DetailView
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class DetailPresenter : MvpPresenter<DetailView>() {

    private lateinit var subscriptions: CompositeDisposable
    private var id: Int = 0
    private var name: String? = null
    private val forecastRepository by lazy {
        ForecastRepository()
    }

    fun onCreate(id: Int, name: String) {
        subscriptions = CompositeDisposable()
        this.id = id
        this.name = name
        viewState.setLocationName(name)
    }

    fun requestForecast() {
        val subscription = forecastRepository
            .requestForecast(id)
            .subscribe(
                {
                        forecastList ->
                    viewState.apply {
                        Log.d("DebugTag", forecastList.toString())
                        setForecast(forecastList)
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