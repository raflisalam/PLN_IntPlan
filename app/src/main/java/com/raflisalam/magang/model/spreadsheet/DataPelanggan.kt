package com.raflisalam.magang.model.spreadsheet

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DataPelanggan(

    @field:SerializedName("no_hp")
    val noHp: String? = null,

    @field:SerializedName("foto_meter_survey")
    val fotoMeterSurvey: String? = null,

    @field:SerializedName("idpel")
    val idpel: String? = null,

    @field:SerializedName("daya")
    val daya: String? = null,

    @field:SerializedName("stan_survey")
    val stanSurvey: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("indikasi")
    val indikasi: String? = null,

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("tarif")
    val tarif: String? = null,

    @field:SerializedName("foto_rumah")
    val fotoRumah: String? = null,

    @field:SerializedName("tagging_survey")
    val taggingSurvey: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("kwh_bln1")
    val kwhBln1: String? = null,

    @field:SerializedName("kwh_bln2")
    val kwhBln2: String? = null,

    @field:SerializedName("tagging_lokasi")
    val taggingLokasi: String? = null,

    @field:SerializedName("kwh_bln3")
    val kwhBln3: String? = null,

    @field:SerializedName("tindak_lanjut")
    val tindakLanjut: String? = null,

    @field:SerializedName("foto_meter")
    val fotoMeter: String? = null,

    @field:SerializedName("stan_rek")
    val stanRek: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(noHp)
        parcel.writeString(fotoMeterSurvey)
        parcel.writeString(idpel)
        parcel.writeString(daya)
        parcel.writeString(stanSurvey)
        parcel.writeString(alamat)
        parcel.writeString(indikasi)
        parcel.writeString(unit)
        parcel.writeString(tarif)
        parcel.writeString(fotoRumah)
        parcel.writeString(taggingSurvey)
        parcel.writeString(nama)
        parcel.writeString(kwhBln1)
        parcel.writeString(kwhBln2)
        parcel.writeString(taggingLokasi)
        parcel.writeString(kwhBln3)
        parcel.writeString(tindakLanjut)
        parcel.writeString(fotoMeter)
        parcel.writeString(stanRek)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataPelanggan> {
        override fun createFromParcel(parcel: Parcel): DataPelanggan {
            return DataPelanggan(parcel)
        }

        override fun newArray(size: Int): Array<DataPelanggan?> {
            return arrayOfNulls(size)
        }
    }
}
