package com.ivan.weatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivan.weatherapp.databinding.WeatherItemBinding
import com.ivan.weatherapp.entity.HourlyWeatherBinding
import javax.inject.Inject

class HourlyWeatherAdapter @Inject constructor(): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    var items: List<HourlyWeatherBinding>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WeatherItemBinding.inflate(layoutInflater, parent, false)
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bindItem(items?.get(position))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}

class HourlyWeatherViewHolder(private val binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindItem(hourlyWeatherBinding: HourlyWeatherBinding?) {
        with(binding){
            weather = hourlyWeatherBinding
            if(hourlyWeatherBinding?.resourceId != null)
                weatherListItemIllustration.setImageResource(hourlyWeatherBinding.resourceId)
        }
    }
}
