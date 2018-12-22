package com.ovchinnikov.anotherweatherapp.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.presentation.WeatherListPresenter
import com.ovchinnikov.anotherweatherapp.ui.main.adapter.WeatherListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class WeatherListActivity : MvpAppCompatActivity(), WeatherListView {

    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var weatherListPresenter: WeatherListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherListPresenter.onCreate()

        val linearLayout = LinearLayoutManager(this)
        weather_list.setHasFixedSize(true)
        weather_list.layoutManager = linearLayout

        if (weather_list.adapter == null) {
            weather_list.adapter = WeatherListAdapter()
        }

        if (savedInstanceState == null && (weather_list.adapter as WeatherListAdapter).itemCount == 0) {
            weatherListPresenter.requestCurrentWeather()
        }
    }

    override fun setWeather(weather: List<WeatherItem>) {
        (weather_list.adapter as WeatherListAdapter).setWeather(weather)
        pb_loading_indicator.visibility = View.GONE
    }

    override fun showSnackbar(errorMessage: String) {
        Snackbar.make(weather_list, errorMessage, Snackbar.LENGTH_LONG).show()
        pb_loading_indicator.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherListPresenter.onDestroy()
    }
}
