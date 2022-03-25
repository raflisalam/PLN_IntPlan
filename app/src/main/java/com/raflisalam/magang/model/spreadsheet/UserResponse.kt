package com.raflisalam.magang.model.spreadsheet

import com.google.gson.annotations.SerializedName
import com.raflisalam.magang.model.spreadsheet.DataPelanggan

data class UserResponse(

	@field:SerializedName("records")
	val records: List<DataPelanggan>
)

