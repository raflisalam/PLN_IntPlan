package com.raflisalam.magang.model.weather

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c")
    var tempC: Double? = null,
    var condition: Condition? = Condition()
)
