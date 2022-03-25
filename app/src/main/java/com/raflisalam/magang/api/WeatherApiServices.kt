package com.raflisalam.magang.api

import com.raflisalam.magang.model.weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiServices {

    @GET("https://api.weatherapi.com/v1/forecast.json?key=a2fda04aafca46879bd150313212211&days=1&aqi=no&alerts=no")
    fun getWeather(
        @Query("q") query: String
    ): Call<Weather>
}