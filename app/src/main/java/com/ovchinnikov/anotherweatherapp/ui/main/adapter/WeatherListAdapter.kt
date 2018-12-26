package com.ovchinnikov.anotherweatherapp.ui.main.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ovchinnikov.anotherweatherapp.commons.WeatherItem
import com.ovchinnikov.anotherweatherapp.commons.adapter.AdapterConstants
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewTypeDelegateAdapter

class WeatherListAdapter(addClickListener: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val addingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.ADDING
    }

    init {
        delegateAdapters.put(AdapterConstants.WEATHER, WeatherDelegateAdapter())
        delegateAdapters.put(AdapterConstants.ADDING, AddingDelegateAdapter(addClickListener))
        items = ArrayList()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun setWeather(weatherList: List<WeatherItem>) {
        val initPosition = getLastPosition()
        if (initPosition != 0) {
            items.removeAt(initPosition)
            notifyItemRemoved(initPosition)
        }
        items.addAll(weatherList)
        items.add(addingItem)
        notifyItemRangeInserted(initPosition, items.size)
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}