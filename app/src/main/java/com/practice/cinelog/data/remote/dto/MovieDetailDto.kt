package com.practice.cinelog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id")             val id: Int,
    @SerializedName("title")          val title: String,
    @SerializedName("overview")       val overview: String,
    @SerializedName("poster_path")    val posterPath: String?,
    @SerializedName("backdrop_path")  val backdropPath: String?,
    @SerializedName("vote_average")   val voteAverage: Double,
    @SerializedName("vote_count")     val voteCount: Int,
    @SerializedName("release_date")   val releaseDate: String?,
    @SerializedName("runtime")        val runtime: Int?,
    @SerializedName("status")         val status: String,
    @SerializedName("tagline")        val tagline: String?,
    @SerializedName("genres")         val genres: List<GenreDto>,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageDto>,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("budget")         val budget: Long,
    @SerializedName("revenue")        val revenue: Long,
    @SerializedName("homepage")       val homepage: String?,
    @SerializedName("imdb_id")        val imdbId: String?
)