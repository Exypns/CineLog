package com.practice.cinelog.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val releaseYear: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val genres: List<Genre>,
    val cast: List<Cast> = emptyList(),
    val directors: List<String> = emptyList(),
    val trailerKey: String? = null,
    val similarMovies: List<Movie> = emptyList()
) {
    val formattedString: String get() = String.format("%.1f", voteAverage)
    val formattedRuntime: String get() {
        if (runtime <= 0) return "N/A"
        val hours = runtime / 60
        val mins = runtime % 60
        return if (hours > 0) "${hours}h ${mins}m" else "${mins}m"
    }
}
