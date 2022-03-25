package com.raflisalam.magang.ui.fragment.maros.viewmodel

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

class MarosViewModel: ViewModel() {

    private val listDataMaros = MutableLiveData<MutableList<DataPelanggan>>()

    fun setDataMaros() {
        ApiClient.instance.getDataMaros().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listDataMaros.postValue(response.body()?.records as MutableList<DataPelanggan>)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure load data!", it) }
            }
        })
    }

    fun getDataMaros(): LiveData<MutableList<DataPelanggan>> {
        return  listDataMaros
    }
}