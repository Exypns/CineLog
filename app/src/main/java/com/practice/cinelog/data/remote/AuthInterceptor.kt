package com.practice.cinelog.data.remote

import android.util.Log
import com.practice.cinelog.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AUTH_CHECK", "Interceptor is running!")

        val token = BuildConfig.TMDB_ACCESS_TOKEN
        Log.d("AUTH_CHECK", "Token starts with: ${token.take(15)}...")

        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
            .header("accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}