package com.practice.cinelog.util

import com.practice.cinelog.BuildConfig

object Constants {
    const val IMAGE_SIZE_POSTER = "w500"
    const val IMAGE_SIZE_BACKDROP = "w1280"
    const val IMAGE_SIZE_PROFILE = "w185"

    fun imageUrl(size: String, path: String?): String =
        "${BuildConfig.TMDB_IMAGE_BASE_URL}$size${path ?: ""}"
}