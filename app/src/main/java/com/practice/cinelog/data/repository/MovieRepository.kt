package com.practice.cinelog.data.repository

import android.util.Log
import com.practice.cinelog.data.remote.api.MovieApiService
import com.practice.cinelog.data.remote.dto.findTrailerKey
import com.practice.cinelog.data.remote.dto.toDirectors
import com.practice.cinelog.data.remote.dto.toDomain
import com.practice.cinelog.data.remote.dto.toDomainList
import com.practice.cinelog.domain.model.Movie
import com.practice.cinelog.domain.model.MovieDetail
import com.practice.cinelog.util.NetworkResult
import com.practice.cinelog.util.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApiService
) {
//    suspend fun getTrending(): List<String> {
//        return try {
//            api.getTrending().results.map { it.title }
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "Error: ${e.message}")
//            emptyList()
//        }
//    }
    suspend fun getTrending(timeWindow: String = "week"): NetworkResult<List<Movie>> =
        safeApiCall { api.getTrending(timeWindow).toDomainList() }

    suspend fun getPopular(): NetworkResult<List<Movie>> =
        safeApiCall { api.getPopular().toDomainList() }

    suspend fun getNowPlaying(): NetworkResult<List<Movie>> =
        safeApiCall { api.getNowPlaying().toDomainList() }

    suspend fun getUpcoming(): NetworkResult<List<Movie>> =
        safeApiCall { api.getUpcoming().toDomainList() }

    suspend fun getTopRated(): NetworkResult<List<Movie>> =
        safeApiCall { api.getTopRated().toDomainList() }

    suspend fun searchMoviews(query: String, page: Int = 1): NetworkResult<List<Movie>> =
        safeApiCall { api.searchMovies(query, page).toDomainList() }

    suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetail> =
        safeApiCall {
            val detail = api.getMovieDetail(movieId).toDomain()
            val credits = api.getMovieCredits(movieId)
            val videos = api.getMovieVideos(movieId)
            val similar = api.getSimilarMovies(movieId).toDomainList()

            detail.copy(
                cast = credits.cast.take(10).map { it.toDomain() },
                directors = credits.toDirectors(),
                trailerKey = videos.findTrailerKey(),
                similarMovies = similar
            )
        }

}