package com.raflisalam.magang.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.magang.api.ApiClient
import com.raflisalam.magang.model.spreadsheet.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel: ViewModel() {

    val status = MutableLiveData<String>()

    fun postDataKarebosi(idpel: String, unit: String, nama: String, alamat: String, noHp: String, tarif: String, daya: String, indikasi: String,
                         stanRek: String, kwhBln1: String, kwhBln2: String, kwhBln3: String, taggingLokasi: String, fotoMeter: String, fotoRumah: String,
                         tindakLanjut: String, stanSurvey: String, taggingSurvey: String, fotoSurvey: String) {
        ApiClient.instance
            .postDataKarebosi(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3,
                taggingLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        status.postValue(response.body()?.status)
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.message?.let { Log.e("failure", it) }
                }
            })
    }

    fun postDataDaya(idpel: String, unit: String, nama: String, alamat: String, noHp: String, tarif: String, daya: String, indikasi: String,
                         stanRek: String, kwhBln1: String, kwhBln2: String, kwhBln3: String, taggingLokasi: String, fotoMeter: String, fotoRumah: String,
                         tindakLanjut: String, stanSurvey: String, taggingSurvey: String, fotoSurvey: String) {
        ApiClient.instance
            .postDataDaya(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3,
                taggingLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        status.postValue(response.body()?.status)
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.message?.let { Log.e("failure", it) }
                }
            })
    }

    fun postDataMaros(idpel: String, unit: String, nama: String, alamat: String, noHp: String, tarif: String, daya: String, indikasi: String,
                         stanRek: String, kwhBln1: String, kwhBln2: String, kwhBln3: String, taggingLokasi: String, fotoMeter: String, fotoRumah: String,
                         tindakLanjut: String, stanSurvey: String, taggingSurvey: String, fotoSurvey: String) {
        ApiClient.instance
            .postDataMaros(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3,
                taggingLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        status.postValue(response.body()?.status)
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.message?.let { Log.e("failure", it) }
                }
            })
    }

    fun postDataPangkep(idpel: String, unit: String, nama: String, alamat: String, noHp: String, tarif: String, daya: String, indikasi: String,
                         stanRek: String, kwhBln1: String, kwhBln2: String, kwhBln3: String, taggingLokasi: String, fotoMeter: String, fotoRumah: String,
                         tindakLanjut: String, stanSurvey: String, taggingSurvey: String, fotoSurvey: String) {
        ApiClient.instance
            .postDataPangkep(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3,
                taggingLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        status.postValue(response.body()?.status)
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.message?.let { Log.e("failure", it) }
                }
            })
    }


    fun getStatus(): LiveData<String> {
        return status
    }
}