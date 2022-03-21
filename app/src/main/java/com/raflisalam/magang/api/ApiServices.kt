package com.raflisalam.magang.api

import com.raflisalam.magang.model.PostResponse
import com.raflisalam.magang.model.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @GET("AKfycbx_XUKr9SHFtxCt5Xy1_S3DdqZdDAKLJBv894PZiyo8cu91JLlsxwfJtMJwX7opRGrZ/exec?action=read")
    fun getDataKarebosi(): Call<UserResponse>

    @GET("AKfycbwMXeITk8KMoSd8iLayPy3JfWZveG06xzduomcgbuk-DDHAV0tdcHyzO56XoLr34SpYHQ/exec?action=read")
    fun getDataDaya(): Call<UserResponse>

    @GET("AKfycbxzen8J_2nU5_6o5OLEzc155EMuXOJbRqCE_U9J0h_81ckDApGHOp2YXzOehuF0aMf4/exec?action=read")
    fun getDataMaros(): Call<UserResponse>

    @GET("AKfycbzAWw0m99e8CfZ0ec7-_PdXGSIWexRsqniy6DLNsQc39pWBV1nNaRFgTsBbcfgN2Fxa/exec?action=read")
    fun getDataPangkep(): Call<UserResponse>

    @FormUrlEncoded
    @POST("AKfycbxeFineOFE4xeFHlpmUIpROrDamOgiZEcs1BjNszWy-xDD9_RcXxQoKpzCDZPGLnt8uDA/exec")
    fun postDataKarebosi(
        @Field("idpel") idpel: String,
        @Field("unit") unit: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("no_hp") noHp: String,
        @Field("tarif") tarif: String,
        @Field("daya") daya: String,
        @Field("indikasi") indikasi: String,
        @Field("stan_rek") stanRek: String,
        @Field("kwh_bln1") kwhBln1: String,
        @Field("kwh_bln2") kwhBln2: String,
        @Field("kwh_bln3") kwhBln3: String,
        @Field("tagging_lokasi") taggingLokasi: String,
        @Field("foto_meter") fotoMeter: String,
        @Field("foto_rumah") fotoRumah: String,
        @Field("tindak_lanjut") tindakLanjut: String,
        @Field("stan_survey") stanSurvey: String
//        @Field("tagging_survey") taggingSurvey: String,
//        @Field("foto_meter_survey") fotoSurvey: String
    ): Call<PostResponse>
}