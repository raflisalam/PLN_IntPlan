package com.raflisalam.magang.api

import com.raflisalam.magang.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {
    @GET("AKfycbx_XUKr9SHFtxCt5Xy1_S3DdqZdDAKLJBv894PZiyo8cu91JLlsxwfJtMJwX7opRGrZ/exec?action=read")
    fun getDataKarebosi(): Call<UserResponse>

    @GET("AKfycbwMXeITk8KMoSd8iLayPy3JfWZveG06xzduomcgbuk-DDHAV0tdcHyzO56XoLr34SpYHQ/exec?action=read")
    fun getDataDaya(): Call<UserResponse>

    @GET("AKfycbxzen8J_2nU5_6o5OLEzc155EMuXOJbRqCE_U9J0h_81ckDApGHOp2YXzOehuF0aMf4/exec?action=read")
    fun getDataMaros(): Call<UserResponse>

    @GET("AKfycbzAWw0m99e8CfZ0ec7-_PdXGSIWexRsqniy6DLNsQc39pWBV1nNaRFgTsBbcfgN2Fxa/exec?action=read")
    fun getDataPangkep(): Call<UserResponse>
}