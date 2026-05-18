package com.practice.cinelog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoResponseDto(
    @SerializedName("id")      val id: Int,
    @SerializedName("results") val results: List<VideoDto>
)

data class VideoDto(
    @SerializedName("id")       val id: String,
    @SerializedName("key")      val key: String,   // YouTube key
    @SerializedName("name")     val name: String,
    @SerializedName("site")     val site: String,  // "YouTube"
    @SerializedName("type")     val type: String,  // "Trailer"
    @SerializedName("official") val official: Boolean
)
