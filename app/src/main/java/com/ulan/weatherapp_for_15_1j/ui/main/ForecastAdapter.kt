package com.ulan.weatherapp_for_15_1j.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ulan.weatherapp_for_15_1j.data.model.ForecastDayDto
import com.ulan.weatherapp_for_15_1j.databinding.ItemForecastBinding


class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForeCastViewHolder>() {
    private var list = ArrayList<ForecastDayDto>()

    fun setList(list: List<ForecastDayDto>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForeCastViewHolder(
        ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ForeCastViewHolder, position: Int) {
        holder.onBind(list[position])
    }


    class ForeCastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(forecast: ForecastDayDto) {
            val day = forecast.day

            Glide.with(binding.root)
                .load("https:${day.condition.icon}")
                .centerCrop()
                .into(binding.itemImg)
            binding.itemTemp.text = "${day.condition.text}, ${day.maxTempC}°C"
            binding.maxTemp.text = "${day.maxTempC}°C↑"
            binding.minTemp.text = "${day.minTempC}°C↓"
        }
    }
}