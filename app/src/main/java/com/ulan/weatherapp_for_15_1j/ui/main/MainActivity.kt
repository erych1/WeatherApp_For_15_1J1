package com.ulan.weatherapp_for_15_1j.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ulan.weatherapp_for_15_1j.R
import com.ulan.weatherapp_for_15_1j.databinding.ActivityMainBinding
import com.ulan.weatherapp_for_15_1j.ui.search.SearchBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val adapter : ForecastAdapter by lazy { ForecastAdapter() }
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var cityName = "Kyrghyzstan , Osh"
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        setTheme(R.style.Theme_WeatherApp_For_15_1J)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mOpenDialog()
        viewModel.getCurrentWeather(cityName)
        binding.rvForecast.adapter = adapter
        observer()
    }

    private fun mOpenDialog(){
        binding.btnLocation.setOnClickListener {
            SearchBottomSheetFragment(this::searchByName).show(supportFragmentManager, "MyCustomDialog")
        }
    }

    private fun searchByName(cityName: String) {
        this.cityName = cityName
        viewModel.getCurrentWeather(cityName)
        observer()
    }


    private fun formatUnixTimestamp(unixTimestamp: Long, zoneId: String): String {
        val instant = Instant.ofEpochSecond(unixTimestamp)
        val zoneDateTime = instant.atZone(ZoneId.of(zoneId))
        val formatter = DateTimeFormatter.ofPattern("Hh' mm'", Locale.ENGLISH)
        return formatter.format(zoneDateTime)
    }


    private fun startUpdateTime() {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")
        runnable = object : Runnable {
            override fun run() {
                val currentTime = LocalDateTime.now()
                val formattedTime = formatter.format(currentTime)
                updateTime(formattedTime)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun updateTime(time: String) {
        binding.dayDataTimeText.text = time
    }

    private fun stopUpdateTime() {
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        startUpdateTime()
    }

    override fun onPause() {
        super.onPause()
        stopUpdateTime()
    }

    @SuppressLint("SetTextI18n")
    fun observer(){
        viewModel.liveData.observe(this) {
            binding.btnLocation.text = "${it.location.country}, ${it.location.name}"
            binding.mainDegree.text = "${it.current.tempC.toInt()}°C"
            binding.txtWeatherSunny.text = it.current.condition.text
            Glide.with(this).load("https:${it.current.condition.icon}").centerCrop().into(binding.imgWeatherSunny)
            binding.txt1.text = it.forecast.forecastDay[0].day.maxTempC
            binding.txt2.text = it.forecast.forecastDay[0].day.minTempC
            binding.txtWeatherHumidityIs.text = "${it.current.humidity}%"
            adapter.setList(it.forecast.forecastDay)
            binding.txtWeatherPressureIs.text = "${it.current.pressuremMb}mBar"
            binding.txtWeatherWindIs.text = "${it.current.windKph}km/h"
            binding.txtSunriseIs.text = it.forecast.forecastDay[0].astro.sunrise
            binding.txtSunsetIs.text = it.forecast.forecastDay[0].astro.sunset
            binding.dayDataTimeText.text =
                formatUnixTimestamp(it.location.localtimeEpoch.toLong(), it.location.zoneId)
        }
    }
}