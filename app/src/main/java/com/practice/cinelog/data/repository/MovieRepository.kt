package com.practice.cinelog.data.repository

import android.util.Log
import com.practice.cinelog.data.remote.api.MovieApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApiService
) {
    suspend fun getTrending(): List<String> {
        return try {
            api.getTrending().results.map { it.title }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error: ${e.message}")
            emptyList()
        }
    }
}