package com.raflisalam.magang.model.weather

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("name")
    var city: String? = null
)
