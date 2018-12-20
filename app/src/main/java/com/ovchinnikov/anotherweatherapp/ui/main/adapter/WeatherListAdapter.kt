package com.ovchinnikov.anotherweatherapp.ui.main.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.commons.adapter.AdapterConstants
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewTypeDelegateAdapter

class WeatherListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val addingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.ADDING
    }

    init {
        delegateAdapters.put(AdapterConstants.WEATHER, WeatherDelegateAdapter())
        delegateAdapters.put(AdapterConstants.ADDING, AddingDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun setWeather(weatherList: List<WeatherItem>) {
        items.addAll(weatherList)
        items.add(addingItem)
        notifyItemRangeInserted(0, items.size)
    }
}