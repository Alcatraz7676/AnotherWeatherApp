package com.ovchinnikov.anotherweatherapp.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
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
            weather_list.adapter = WeatherListAdapter { addItemClicked() }
        }

        if (savedInstanceState == null && (weather_list.adapter as WeatherListAdapter).itemCount == 0) {
            weatherListPresenter.requestCurrentWeatherList()
        }

        reload_button.setOnClickListener {
            weatherListPresenter.requestCurrentWeatherList()
            hideErrorView()
            showLoading()
        }
    }

    private fun addItemClicked() {
        showDialog()
    }

    private fun showDialog() {
        val alert = AlertDialog.Builder(this)

        val dialogView = layoutInflater.inflate(R.layout.adding_dialog, null)
        val editText = dialogView.findViewById<EditText>(R.id.city_input)
        with (alert) {
            setTitle("Введите название города")
            setPositiveButton("ОК") {
                    dialog, _ ->
                dialog.dismiss()
                weatherListPresenter.requestCurrentWeatherCity(editText.text.toString())
            }
        }
        val dialog = alert.create()
        dialog.setView(dialogView)
        dialog.show()
    }

    override fun setWeather(weather: List<WeatherItem>) {
        (weather_list.adapter as WeatherListAdapter).setWeather(weather)
    }

    override fun showSnackbar(errorMessage: String) {
        Snackbar.make(weather_list, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun showErrorView() {
        empty_view.visibility = VISIBLE
    }

    override fun hideErrorView() {
        empty_view.visibility = GONE
    }

    override fun showLoading() {
        pb_loading_indicator.visibility = VISIBLE
    }

    override fun hideLoading() {
        pb_loading_indicator.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherListPresenter.onDestroy()
    }
}
