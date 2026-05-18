package com.practice.cinelog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditsResponseDto(
    @SerializedName("id")   val id: Int,
    @SerializedName("cast") val cast: List<CastDto>,
    @SerializedName("crew") val crew: List<CrewDto>
)

data class CastDto(
    @SerializedName("id")           val id: Int,
    @SerializedName("name")         val name: String,
    @SerializedName("character")    val character: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("order")        val order: Int
)

data class CrewDto(
    @SerializedName("id")           val id: Int,
    @SerializedName("name")         val name: String,
    @SerializedName("job")          val job: String,
    @SerializedName("profile_path") val profilePath: String?
)
