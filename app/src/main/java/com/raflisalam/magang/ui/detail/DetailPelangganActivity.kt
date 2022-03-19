package com.raflisalam.magang.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.magang.databinding.ActivityDetailPelangganBinding
import com.raflisalam.magang.model.DataPelanggan

class DetailPelangganActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPelangganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPelangganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParcelableData()
    }

    private fun getParcelableData() {
        val data: DataPelanggan = intent.getParcelableExtra<DataPelanggan>(DETAIL_DATA) as DataPelanggan
        setDetailData(data)
    }

    private fun setDetailData(data: DataPelanggan) {
        binding.apply {
            idPelanggan.text = data.idpel
            tvNamaPelanggan.text = data.nama
            tvALamatPelanggan.text = data.alamat
            jmlhTarif.text = data.tarif
            jmlhDaya.text = data.daya
            angkaStand.text = data.stanRek
            riwayatBlnPertama.text = data.kwhBln1
            riwayatBlnKedua.text = data.kwhBln2
            riwayatBlnKetiga.text = data.kwhBln3
            tvIndikasi.text = data.indikasi
            tagLokasi.text = data.taggingLokasi
            fotoMeter.text = data.fotoMeter
            fotoRumah.text = data.fotoRumah
        }
    }

    companion object {
        const val DETAIL_DATA = "detail"
    }
}