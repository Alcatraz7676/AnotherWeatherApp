package com.ovchinnikov.anotherweatherapp.ui.detail.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.commons.ForecastItem
import com.ovchinnikov.anotherweatherapp.commons.WeatherIcon
import com.ovchinnikov.anotherweatherapp.commons.inflate
import kotlinx.android.synthetic.main.forecast_item.view.*
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ForecastItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.bind(items[position])
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.forecast_item)) {

        fun bind(item: ForecastItem) = with(itemView) {
            when(item.weatherIcon) {
                WeatherIcon.CLEAR -> weather_icon.setImageResource(R.drawable.art_clear)
                WeatherIcon.CLOUDY -> weather_icon.setImageResource(R.drawable.art_clouds)
                WeatherIcon.FOG -> weather_icon.setImageResource(R.drawable.art_fog)
                WeatherIcon.LIGHT_CLOUDS -> weather_icon.setImageResource(R.drawable.art_light_clouds)
                WeatherIcon.LIGHT_RAIN -> weather_icon.setImageResource(R.drawable.art_light_rain)
                WeatherIcon.RAIN -> weather_icon.setImageResource(R.drawable.art_rain)
                WeatherIcon.SNOW -> weather_icon.setImageResource(R.drawable.art_snow)
                WeatherIcon.STORM -> weather_icon.setImageResource(R.drawable.art_storm)
                else -> weather_icon.setImageResource(R.mipmap.ic_launcher_round)
            }
            max_temp.text = String.format(context.getString(R.string.format_temperature), item.tempMax)
            min_temp.text = String.format(context.getString(R.string.format_temperature), item.tempMin)
            date.text = item.date.get(Calendar.DAY_OF_MONTH).toString() + "." + (item.date.get(Calendar.MONTH) + 1) + "." + item.date.get(Calendar.YEAR)
            time.text = item.date.get(Calendar.HOUR_OF_DAY).toString() + ":" + item.date.get(Calendar.MINUTE)
            if (item.date.get(Calendar.MINUTE) < 10)
                time.text = time.text.toString() + "0"
        }
    }

    override fun getItemCount() = items.size

    fun setForecast(forecastList: List<ForecastItem>) {
        items.addAll(forecastList)
        notifyItemRangeInserted(0, items.size)
    }

}