package com.practice.cinelog.data.remote.dto

import com.practice.cinelog.domain.model.Cast
import com.practice.cinelog.domain.model.Genre
import com.practice.cinelog.domain.model.Movie
import com.practice.cinelog.domain.model.MovieDetail
import com.practice.cinelog.util.Constants

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterUrl = Constants.imageUrl(Constants.IMAGE_SIZE_POSTER, posterPath),
    backdropUrl = Constants.imageUrl(Constants.IMAGE_SIZE_BACKDROP, backdropPath),
    voteAverage = voteAverage,
    voteCount = voteCount,
    releaseDate = releaseDate.orEmpty(),
    releaseYear = releaseDate?.take(4).orEmpty(),
    genreIds = genreIds,
    popularity = popularity
)

fun MovieResponseDto.toDomainList(): List<Movie> = results.map { it.toDomain() }

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterUrl = Constants.imageUrl(Constants.IMAGE_SIZE_POSTER, posterPath),
    backdropUrl = Constants.imageUrl(Constants.IMAGE_SIZE_BACKDROP, backdropPath),
    voteAverage = voteAverage,
    voteCount = voteCount,
    releaseDate = releaseDate.orEmpty(),
    releaseYear = releaseDate?.take(4).orEmpty(),
    runtime = runtime ?: 0,
    status = status,
    tagline = tagline.orEmpty(),
    genres = genres.map { it.toDomain() }
)

fun GenreDto.toDomain(): Genre = Genre(id = id, name = name)

fun CastDto.toDomain(): Cast = Cast(
    id = id,
    name = name,
    character  = character,
    profileUrl = Constants.imageUrl(Constants.IMAGE_SIZE_PROFILE, profilePath)
)

fun CreditsResponseDto.toDirectors(): List<String> =
    crew.filter { it.job == "Director" }.map { it.name }

fun VideoResponseDto.findTrailerKey(): String? =
    results
        .filter { it.site == "YouTube" && it.type == "Trailer" && it.official }
        .firstOrNull()?.key
        ?: results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }?.key