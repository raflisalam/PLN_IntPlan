package com.raflisalam.magang.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.TextUtilsCompat
import androidx.lifecycle.ViewModelProvider
import com.raflisalam.magang.R
import com.raflisalam.magang.databinding.ActivityInputSurveyBinding
import com.raflisalam.magang.model.DataPelanggan
import com.raflisalam.magang.ui.viewmodel.PostViewModel

class InputSurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputSurveyBinding
    private lateinit var viewModel: PostViewModel
    private val tagging: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParcelableData()
        setupViewModel()
    }

    private fun getParcelableData() {
        val data: DataPelanggan = intent.getParcelableExtra<DataPelanggan>(DETAIL_DATA) as DataPelanggan
        setupButton(data)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PostViewModel::class.java)
    }

    private fun setupButton(data: DataPelanggan) {
        binding.apply {
            btnSubmit.setOnClickListener {
                setPostData(data)
            }
        }
    }

    private fun setPostData(data: DataPelanggan) {
        with(data) {
            val idpel = idpel!!
            val unit = unit!!
            val nama = nama!!
            val alamat = alamat!!
            val noHp = noHp!!
            val tarif = tarif!!
            val daya = daya!!
            val indikasi = indikasi!!
            val stanRek = stanRek!!
            val kwhBln1 = kwhBln1!!
            val kwhBln2 = kwhBln2!!
            val kwhBln3 = kwhBln3!!
            val tagLokasi = taggingLokasi!!
            val fotoMeter = fotoMeter!!
            val fotoRumah = fotoRumah!!

            //Spinner
            val adapterSpinner: ArrayAdapter<*> = ArrayAdapter.createFromResource(this@InputSurveyActivity, R.array.pilihan_tindaklanjut, R.layout.list_tindaklanjut)
            adapterSpinner.setDropDownViewResource(R.layout.list_tindaklanjut)
            binding.spinner.adapter = adapterSpinner

            val tindakLanjut: String = binding.spinner.selectedItem.toString().toUpperCase()
            val stanSurvey = binding.inputStanSurvey.text.toString()
            if (TextUtils.isEmpty(stanSurvey)) {
                binding.inputStanSurvey.error = "Kolom ini tidak boleh kosong1"
            }

            viewModel.postData(
                idpel,
                unit,
                nama,
                alamat,
                noHp,
                tarif,
                daya,
                indikasi,
                stanRek,
                kwhBln1,
                kwhBln2,
                kwhBln3,
                tagLokasi,
                fotoMeter,
                fotoRumah,
                tindakLanjut,
                stanSurvey,
            )
        }
        getStatus()
    }

    private fun getStatus() {
        viewModel.getStatus().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        const val DETAIL_DATA = "detail"
    }
}