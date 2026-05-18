package com.practice.cinelog.data.repository

import com.practice.cinelog.data.local.db.MovieDao
import com.practice.cinelog.data.local.db.MovieEntity
import com.practice.cinelog.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistRepository @Inject constructor(
    private val dao: MovieDao
) {
    fun getWatchlist(): Flow<List<MovieEntity>> = dao.getWatchlist()

    fun getFavorite(): Flow<List<MovieEntity>> = dao.getFavorite()

    fun isInWatchlist(movieId: Int): Flow<Boolean> = dao.isInWatchlist(movieId)

    fun isInFavorite(movieId: Int): Flow<Boolean> = dao.isInFavorite(movieId)

    suspend fun addToWatchlist(movie: MovieDetail) {
        val existing = dao.getMovieById(movie.id)
        if (existing == null) {
            dao.insertMovie(movie.toEntity(isWatchlist = true))
        } else {
            dao.updateWatchlist(movie.id, true)
        }
    }

    suspend fun removeFromWatchlist(movieId: Int) {
        dao.updateWatchlist(movieId, false)
    }

    suspend fun addToFavorite(movie: MovieDetail) {
        val existing = dao.getMovieById(movie.id)
        if (existing == null) {
            dao.insertMovie(movie.toEntity(isFavorite = true))
        } else {
            dao.updateFavorite(movie.id, true)
        }
    }

    suspend fun removeFromFavorite(movieId: Int) {
        dao.updateFavorite(movieId, false)
    }
}

fun MovieDetail.toEntity(
    isWatchlist: Boolean = false,
    isFavorite: Boolean = false
) = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    overview = overview,
    voteAverage = voteAverage,
    releaseYear = releaseYear,
    isWatchlist = isWatchlist,
    isFavorite  = isFavorite
)