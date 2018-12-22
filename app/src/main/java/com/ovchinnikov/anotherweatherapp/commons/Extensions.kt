package com.ovchinnikov.anotherweatherapp.commons

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.db.CityId
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl))
        Picasso.get().load(R.mipmap.ic_launcher).into(this)
    else
        Picasso.get().load(imageUrl).into(this)
}

fun ImageView.setImageFromResource(drawable: Int) {
    Picasso.get().load(drawable).into(this)
}

fun Int.getWeatherState() = when(this) {
    in 200..232 -> WeatherIcon.STORM
    in 300..321 -> WeatherIcon.RAIN
    in 500..504 -> WeatherIcon.LIGHT_RAIN
    511 -> WeatherIcon.SNOW
    in 520..531 -> WeatherIcon.RAIN
    in 600..622 -> WeatherIcon.SNOW
    in 701..781 -> WeatherIcon.FOG
    800 -> WeatherIcon.CLEAR
    801 -> WeatherIcon.LIGHT_CLOUDS
    in 802..804 -> WeatherIcon.CLOUDY
    else -> WeatherIcon.NONE
}

class Utils {
    companion object {
        val DefaultIdsList = arrayOf(
            CityId(524901),
            CityId(498817),
            CityId(1496747),
            CityId(1486209),
            CityId(520555),
            CityId(551487),
            CityId(1508291),
            CityId(1496153),
            CityId(499099),
            CityId(501175),
            CityId(479561),
            CityId(1502026),
            CityId(511196),
            CityId(472045),
            CityId(472757)
        )
    }
}