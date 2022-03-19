package com.raflisalam.magang.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("records")
	val records: List<DataPelanggan>
)

