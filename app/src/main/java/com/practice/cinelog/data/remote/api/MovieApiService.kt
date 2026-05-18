package com.practice.cinelog.data.remote.api

import com.practice.cinelog.data.remote.dto.CreditsResponseDto
import com.practice.cinelog.data.remote.dto.GenreResponseDto
import com.practice.cinelog.data.remote.dto.MovieDetailDto
import com.practice.cinelog.data.remote.dto.MovieResponseDto
import com.practice.cinelog.data.remote.dto.VideoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    // Trending
    @GET("trending/movie/{time_window}")
    suspend fun getTrending(
        @Path("time_window") timeWindow: String = "week", // "day" or "week"
        @Query("page") page: Int = 1
    ): MovieResponseDto

    // Discovery
    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int = 1): MovieResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int = 1): MovieResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int = 1): MovieResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page: Int = 1): MovieResponseDto

    // Search
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieResponseDto

    // Movie detail
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetailDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CreditsResponseDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): VideoResponseDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): MovieResponseDto

    // Genres
    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponseDto
}