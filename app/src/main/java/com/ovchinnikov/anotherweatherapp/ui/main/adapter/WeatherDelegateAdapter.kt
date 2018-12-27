package com.ovchinnikov.anotherweatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.commons.WeatherIcon
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewTypeDelegateAdapter
import com.ovchinnikov.anotherweatherapp.commons.inflate
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherDelegateAdapter(private val weatherClickListener: (Int, String) -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ViewHolder
        holder.bind(item as WeatherItem, weatherClickListener)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.weather_item)) {

        fun bind(item: WeatherItem, clickListener: (Int, String) -> Unit) = with(itemView) {
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
            city.text = item.locationName
            weather_description.text = item.weatherName
            high_temperature.text = String.format(context.getString(R.string.format_temperature), item.tempMax)
            low_temperature.text = String.format(context.getString(R.string.format_temperature), item.tempMin)
            itemView.setOnClickListener { clickListener(item.id, item.locationName) }
        }
    }
}