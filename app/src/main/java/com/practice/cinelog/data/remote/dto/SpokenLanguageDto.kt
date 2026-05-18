package com.practice.cinelog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SpokenLanguageDto(
    @SerializedName("iso_639_1")      val iso: String,
    @SerializedName("english_name")   val englishName: String
)

data class ProductionCompanyDto(
    @SerializedName("id")         val id: Int,
    @SerializedName("name")       val name: String,
    @SerializedName("logo_path")  val logoPath: String?,
    @SerializedName("origin_country") val originCountry: String
)
