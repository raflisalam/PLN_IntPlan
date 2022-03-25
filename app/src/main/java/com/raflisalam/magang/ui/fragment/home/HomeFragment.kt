package com.raflisalam.magang.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.raflisalam.magang.R
import com.raflisalam.magang.databinding.FragmentHomeBinding
import com.raflisalam.magang.ui.viewmodel.WeatherViewModel
import java.util.*

class HomeFragment : Fragment() {

    private var _homeFragment: FragmentHomeBinding? = null
    private val binding get() = _homeFragment as FragmentHomeBinding

    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var viewModel: WeatherViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _homeFragment = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grettingsText()
        setupViewModel()
        getCurrentLocation()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(WeatherViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentLocation() {
        fusedLocation = LocationServices.getFusedLocationProviderClient(context)
        val locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
            }
            fusedLocation.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val address: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 10)
                    if (address != null) {
                        for (adr in address) {
                            val cityName = adr.subAdminArea
                            if (cityName != null) {
                                binding.tvLocation.text = cityName
                                getWeatherData(cityName)
                            }
                        }
                    }
                } else {
                    val locationRequest = com.google.android.gms.location.LocationRequest()
                        .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)

                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val location1 = locationResult.lastLocation
                            val cityName = (location1.latitude.toString() + "," + location1.longitude)
                            getWeatherData(cityName)
                        }
                    }
                    fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getWeatherData(cityName: String) {
        viewModel.setWeather(cityName)
        viewModel.getWeather().observe(this, {
            binding.apply {
                tvTemp.text = it.current?.tempC?.toInt().toString() + "°"
                tvCondition.text = it.current?.condition?.text
                val strCondition = it.current?.condition?.text
                if (strCondition != null) {
                    setIconWeather(strCondition)
                }
            }
        })
    }

    private fun setIconWeather(string: String) {
        if (string == "Light rain" || string == "Light rain shower" || string == "Moderate rain" || string == "Patchy rain possible") {
            binding.iconTemp.setImageResource(R.drawable.ic_light_rain)
        } else if (string == "Partly cloudy") {
            binding.iconTemp.setImageResource(R.drawable.ic_partly_cloudy)
        } else if (string == "Overcast") {
            binding.iconTemp.setImageResource(R.drawable.ic_overcast)
        } else if (string == "Fog") {
            binding.iconTemp.setImageResource(R.drawable.ic_fog)
        } else if (string == "Clear" || string == "Sunny") {
            binding.iconTemp.setImageResource(R.drawable.ic_clear)
        } else if (string == "Light snow") {
            binding.iconTemp.setImageResource(R.drawable.ic_light_snow)
        } else if (string == "Heavy snow") {
            binding.iconTemp.setImageResource(R.drawable.ic_heavy_snow)
        } else if (string == "Blizzard") {
            binding.iconTemp.setImageResource(R.drawable.ic_blizzard)
        }
    }

    private fun grettingsText() {
        val date = Calendar.getInstance()
        val hour = date.get(Calendar.HOUR_OF_DAY)
        if (hour in 3..9) {
            binding.tvUcapan.text = "Selamat Pagi"
        } else if (hour in 10..14) {
            binding.tvUcapan.text = "Selamat Siang"
        } else if (hour in 15..18) {
            binding.tvUcapan.text = "Selamat Sore"
        } else {
            binding.tvUcapan.text = "Selamat Malam"
        }
    }
}