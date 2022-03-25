package com.raflisalam.magang.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.raflisalam.magang.R
import com.raflisalam.magang.databinding.ActivityInputSurveyBinding
import com.raflisalam.magang.model.spreadsheet.DataPelanggan
import com.raflisalam.magang.model.spreadsheet.UrlImageResponse
import com.raflisalam.magang.ui.viewmodel.PostViewModel

class InputSurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputSurveyBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var uriImage: Uri
    private var tagging: String = ""
    private var namaUrl: String = ""
    private var urlId: String = ""

    private lateinit var db: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbImage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParcelableData()
        setupViewModel()
        setupSpinner()
        setupFirebase()
    }

    private fun setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://intel-platinum-401ac-default-rtdb.asia-southeast1.firebasedatabase.app")
        db = firebaseDatabase.getReference("Image")
        dbImage = FirebaseStorage.getInstance().reference
    }

    private fun setupSpinner() {
        val adapterSpinner: ArrayAdapter<*> = ArrayAdapter.createFromResource(this@InputSurveyActivity, R.array.pilihan_tindaklanjut, R.layout.list_tindaklanjut)
        adapterSpinner.setDropDownViewResource(R.layout.list_tindaklanjut)
        binding.spinner.adapter = adapterSpinner

    }

    private fun getParcelableData() {
        val data: DataPelanggan = intent.getParcelableExtra<DataPelanggan>(DETAIL_DATA) as DataPelanggan
        binding.dataIdpel.text = data.idpel
        setupButton(data)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PostViewModel::class.java)
    }

    private fun setupButton(data: DataPelanggan) {
        binding.apply {
            btnSubmit.setOnClickListener {
                if (TextUtils.isEmpty(inputStanSurvey.toString())) {
                    inputStanSurvey.error = "Form ini tidak boleh kosong!"
                } else {
                    when (data.unit) {
                        "KAREBOSI" -> {
                            setPostKarebosi(data)
                        }
                        "DAYA" -> {
                            setPostDaya(data)
                        }
                        "MAROS" -> {
                            setPostMaros(data)
                        }
                        "PANGKEP" -> {
                            setPostPangkep(data)
                        }
                    }
                }
            }

            btnGetLocation.setOnClickListener {
                getTaggingLocation()
            }

            imgPreview.setOnClickListener {
                ImagePicker.Companion.with(this@InputSurveyActivity)
                    .compress(100)
                    .maxResultSize(1000, 1000)
                    .start(2)
                binding.loadImage.visibility = View.VISIBLE
            }

            btnUpload.setOnClickListener {
                uploadToFirebase(uriImage)
            }
        }
    }

    private fun getTaggingLocation() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this@InputSurveyActivity)
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this@InputSurveyActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@InputSurveyActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this@InputSurveyActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
            }
            fusedLocationProvider.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null) {
                    tagging = (location.latitude.toString() + "," + location.longitude)
                    binding.tvCurrentLocation.text = tagging
                } else {
                    val locationRequest = com.google.android.gms.location.LocationRequest()
                        .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)

                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val location1 = locationResult.lastLocation
                            tagging = (location1.latitude.toString() + "," + location1.longitude)
                            binding.tvCurrentLocation.text = tagging
                        }
                    }
                    fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==  2 && resultCode == RESULT_OK && data !=null) {
            uriImage = data.data!!
            binding.imgPreview.setImageURI(uriImage)
            if (binding.imgPreview !=null) {
                binding.loadImage.visibility = View.INVISIBLE
                binding.tvNamaFile.visibility = View.INVISIBLE
            }
        }
    }

    private fun uploadToFirebase(uri: Uri) {
        val fileImage = dbImage.child(System.currentTimeMillis().toString() + getFileExtension(uri))
        fileImage.putFile(uri).addOnSuccessListener {
            fileImage.downloadUrl.addOnSuccessListener {
                val url = UrlImageResponse(it.toString())
                urlId = db.push().key.toString()
                db.child(urlId).setValue(url)
                getImageUrl()
                binding.loadImage.visibility = View.INVISIBLE
                binding.btnUpload.visibility = View.INVISIBLE
                Toast.makeText(this, "Foto Berhasil di Upload", Toast.LENGTH_SHORT).show()
            }
        }.addOnProgressListener {
            binding.loadImage.visibility = View.VISIBLE
        }.addOnFailureListener {
            binding.loadImage.visibility = View.INVISIBLE
            Toast.makeText(this, "Upload foto gagal!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImageUrl() {
        val urlImage = urlId
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(urlImage)) {
                    val getUrlImage = snapshot.child(urlImage).child("imageUrl").value.toString()
                    namaUrl = getUrlImage
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getFileExtension(uriImg: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uriImg))
    }

    private fun setPostKarebosi(data: DataPelanggan) {
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

            val taggingSurvey = tagging
            val tindakLanjut: String = binding.spinner.selectedItem.toString().toUpperCase()
            val stanSurvey = binding.inputStanSurvey.text.toString()
            val fotoSurvey = namaUrl

            viewModel.postDataKarebosi(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3, tagLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
        }
        finish()
        getStatus()
    }

    private fun setPostDaya(data: DataPelanggan) {
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

            val taggingSurvey = tagging
            val tindakLanjut: String = binding.spinner.selectedItem.toString().toUpperCase()
            val stanSurvey = binding.inputStanSurvey.text.toString()
            val fotoSurvey = namaUrl

            viewModel.postDataDaya(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3, tagLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
        }
        finish()
        getStatus()
    }

    private fun setPostMaros(data: DataPelanggan) {
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

            val taggingSurvey = tagging
            val tindakLanjut: String = binding.spinner.selectedItem.toString().toUpperCase()
            val stanSurvey = binding.inputStanSurvey.text.toString()
            val fotoSurvey = namaUrl

            viewModel.postDataMaros(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3, tagLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
        }
        finish()
        getStatus()
    }

    private fun setPostPangkep(data: DataPelanggan) {
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

            val taggingSurvey = tagging
            val tindakLanjut: String = binding.spinner.selectedItem.toString().toUpperCase()
            val stanSurvey = binding.inputStanSurvey.text.toString()
            val fotoSurvey = namaUrl

            viewModel.postDataPangkep(idpel, unit, nama, alamat, noHp, tarif, daya, indikasi, stanRek, kwhBln1, kwhBln2, kwhBln3, tagLokasi, fotoMeter, fotoRumah, tindakLanjut, stanSurvey, taggingSurvey, fotoSurvey)
        }
        finish()
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