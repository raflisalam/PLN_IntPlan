package com.raflisalam.magang.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val SPREADSHEET_URL = "https://script.google.com/macros/s/"
    private const val WEATHER_URL = "http://api.weatherapi.com/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: ApiServices by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(SPREADSHEET_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(ApiServices::class.java)
    }

    val weatherInstance: WeatherApiServices by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(WeatherApiServices::class.java)
    }
}