package com.practice.cinelog.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val releaseYear: String,
    val genreIds: List<Int>,
    val popularity: Double
) {
    val formattedString: String get() = String.format("%.1f", voteAverage)
    val starRating: Float get() = (voteAverage / 2).toFloat()
}
