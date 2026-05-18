package com.practice.cinelog.data.remote.api

import com.practice.cinelog.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    val apiService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}