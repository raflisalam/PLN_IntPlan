package com.raflisalam.magang.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.magang.api.ApiClient
import com.raflisalam.magang.api.WeatherApiServices
import com.raflisalam.magang.model.weather.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {

    val weatherData = MutableLiveData<Weather>()

    fun setWeather(query: String) {
        ApiClient.weatherInstance.getWeather(query).enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    weatherData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                t.message?.let { Log.d("Fail load weather data", it) }
            }

        })
    }

    fun getWeather(): LiveData<Weather> {
        return weatherData
    }
}