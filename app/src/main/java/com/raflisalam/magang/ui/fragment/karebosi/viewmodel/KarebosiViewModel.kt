package com.raflisalam.magang.ui.fragment.karebosi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.magang.api.ApiClient
import com.raflisalam.magang.model.DataPelanggan
import com.raflisalam.magang.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KarebosiViewModel: ViewModel() {

    val listDataKarebosi = MutableLiveData<MutableList<DataPelanggan>>()

    fun setDataKarebosi() {
        ApiClient.instance.getDataKarebosi().enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listDataKarebosi.postValue(response.body()?.records as MutableList<DataPelanggan>?)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure load data!", it) }
            }
        })
    }

    fun getDataKarebosi(): LiveData<MutableList<DataPelanggan>> {
        return listDataKarebosi
    }
}