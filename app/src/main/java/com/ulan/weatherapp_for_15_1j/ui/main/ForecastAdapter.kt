package com.ulan.weatherapp_for_15_1j.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ulan.weatherapp_for_15_1j.data.model.ForecastDayDto
import com.ulan.weatherapp_for_15_1j.databinding.ItemForecastBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


class ForecastAdapter : ListAdapter<ForecastDayDto,ForecastAdapter.ForecastViewHolder>(ForecastDiffUtill()) {
    class ForecastViewHolder(private val binding: ItemForecastBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(forecastModel: ForecastDayDto?) {
            Glide.with(binding.root).load("https:${forecastModel?.day?.condition?.icon}").into(binding.itemImg)
            val unixTime = forecastModel?.dateEpoch
            binding.itemTemp.text = "${ unixTime?.toLong()?.let { formatUnixTimestamp(it) }} / ${forecastModel?.day?.avgtemp}°C"
            binding.maxTemp.text = "${forecastModel?.day?.maxTempC}°C↑"
            binding.minTemp.text = "${forecastModel?.day?.minTempC}°C↓"
        }

        private fun formatUnixTimestamp(unixTimestamp: Long): String {
            val instant = Instant.ofEpochSecond(unixTimestamp)
            val formatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)
                .withZone(ZoneId.systemDefault())
            return formatter.format(instant)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent , false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastModel = getItem(position)
        holder.onBind(forecastModel)
    }
}

class ForecastDiffUtill : DiffUtil.ItemCallback<ForecastDayDto>() {
    override fun areItemsTheSame(oldItem: ForecastDayDto, newItem: ForecastDayDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ForecastDayDto, newItem: ForecastDayDto): Boolean {
        return oldItem == newItem
    }

}
