package com.ovchinnikov.anotherweatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ovchinnikov.anotherweatherapp.R
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewType
import com.ovchinnikov.anotherweatherapp.commons.adapter.ViewTypeDelegateAdapter
import com.ovchinnikov.anotherweatherapp.commons.inflate

class AddingDelegateAdapter(private val addClickListener: () -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ViewHolder
        holder.bind(addClickListener)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.weather_item_adding)) {

        fun bind(clickListener: () -> Unit) {
            itemView.setOnClickListener { clickListener() }
        }
    }


}