package com.raflisalam.magang.ui.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raflisalam.magang.R
import com.raflisalam.magang.databinding.ActivityDetailPelangganBinding
import com.raflisalam.magang.model.DataPelanggan
import com.raflisalam.magang.ui.InputSurveyActivity

class DetailPelangganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPelangganBinding
    private var clicked: Boolean = false
    private val REQUEST_CALL = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPelangganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParcelableData()
    }

    private fun getParcelableData() {
        val data: DataPelanggan = intent.getParcelableExtra<DataPelanggan>(DETAIL_DATA) as DataPelanggan
        setDetailData(data)
        setupButton(data)
    }

    private fun setupButton(data: DataPelanggan) {
        binding.apply {
            btnFloat.setOnClickListener {
                setVisibilty(clicked)
                setAnimation(clicked)
                clicked = !clicked
            }

            btnCall.setOnClickListener {
                callingPelanggan(data)
            }

            btnInput.setOnClickListener {
                val intent = Intent(this@DetailPelangganActivity, InputSurveyActivity::class.java)
                intent.putExtra(InputSurveyActivity.DETAIL_DATA, data)
                startActivity(intent)
            }

            btnBack.setOnClickListener {
                onBackPressed()
                finish()
            }
        }
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

    private fun callingPelanggan(data: DataPelanggan) {
        val nomor = "tel:62${data.noHp}"
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL)
        } else {
            if (nomor.length <= 6) {
                Toast.makeText(this, "Pelanggan tidak memiliki nomor hp", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(nomor)))
            }
        }
    }

    private fun setAnimation(clicked: Boolean) {
        val rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
        val rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
        val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)

        binding.apply {
            if (!clicked) {
                btnInput.startAnimation(fromBottom)
                btnCall.startAnimation(fromBottom)
                btnFloat.startAnimation(rotateOpen)
            } else {
                btnInput.startAnimation(toBottom)
                btnCall.startAnimation(toBottom)
                btnFloat.startAnimation(rotateClose)
            }
        }
    }

    private fun setVisibilty(clicked: Boolean) {
        binding.apply {
            if (!clicked) {
                btnInput.visibility = View.VISIBLE
                btnCall.visibility = View.VISIBLE
            } else {
                btnInput.visibility = View.INVISIBLE
                btnCall.visibility = View.INVISIBLE
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val DETAIL_DATA = "detail"
    }
}