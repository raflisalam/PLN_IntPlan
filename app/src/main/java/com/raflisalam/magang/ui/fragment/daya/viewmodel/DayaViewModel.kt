package com.raflisalam.magang.ui.fragment.daya.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.magang.api.ApiClient
import com.raflisalam.magang.model.spreadsheet.DataPelanggan
import com.raflisalam.magang.model.spreadsheet.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayaViewModel: ViewModel() {

    private val listDataDaya = MutableLiveData<MutableList<DataPelanggan>>()

    fun setDataDaya() {
        ApiClient.instance.getDataDaya().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listDataDaya.postValue(response.body()?.records as MutableList<DataPelanggan>?)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure load data!", it) }
            }
        })
    }

    fun getDataDaya(): LiveData<MutableList<DataPelanggan>> {
        return listDataDaya
    }
}