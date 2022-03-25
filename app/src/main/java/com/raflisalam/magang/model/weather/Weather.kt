package com.raflisalam.magang.model.weather

data class Weather(
    var current: Current? = Current(),
    var location: Location? = Location()
)