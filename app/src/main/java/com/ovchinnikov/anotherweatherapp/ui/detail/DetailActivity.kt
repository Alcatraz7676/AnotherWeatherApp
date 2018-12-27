package com.ovchinnikov.anotherweatherapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.commons.ForecastItem
import com.ovchinnikov.anotherweatherapp.presentation.DetailPresenter
import com.ovchinnikov.anotherweatherapp.ui.detail.adapter.ForecastAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : MvpAppCompatActivity(), DetailView {

    companion object {

        private const val INTENT_CITY_ID = "city_id"
        private const val INTENT_CITY_NAME = "city_name"

        fun newIntent(context: Context, id: Int, name: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_CITY_ID, id)
            intent.putExtra(INTENT_CITY_NAME, name)
            return intent
        }
    }

    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra(INTENT_CITY_ID, 0)
        val name = intent.getStringExtra(INTENT_CITY_NAME)

        detailPresenter.onCreate(id, name)

        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        forecast_list.setHasFixedSize(true)
        forecast_list.layoutManager = linearLayout
        forecast_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        forecast_list.isNestedScrollingEnabled = false

        if (forecast_list.adapter == null) {
            forecast_list.adapter = ForecastAdapter()
        }

        if (savedInstanceState == null && (forecast_list.adapter as ForecastAdapter).itemCount == 0) {
            detailPresenter.requestForecast()
        }

        reload_button.setOnClickListener {
            detailPresenter.requestForecast()
            hideErrorView()
            showLoading()
        }
    }

    override fun setLocationName(name: String) {
        city_name.text = name
    }

    override fun setForecast(forecast: List<ForecastItem>) {
        (forecast_list.adapter as ForecastAdapter).setForecast(forecast)
    }

    override fun showSnackbar(errorMessage: String) {
        Snackbar.make(forecast_list, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun showErrorView() {
        error_view.visibility = View.VISIBLE
    }

    override fun hideErrorView() {
        error_view.visibility = View.GONE
    }

    override fun showLoading() {
        pb_loading_indicator.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_loading_indicator.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()
        return true
    }
}
