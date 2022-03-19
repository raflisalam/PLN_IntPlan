package com.raflisalam.magang.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://script.google.com/macros/s/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: ApiServices by lazy {
        val retroift = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retroift.create(ApiServices::class.java)
    }
}