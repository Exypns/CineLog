package com.practice.cinelog.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.cinelog.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM watchlist WHERE isWatchlist = 1 ORDER BY addedAt DESC")
    fun getWatchlist(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM watchlist WHERE isFavorite = 1 ORDER BY addedAt DESC")
    fun getFavorite(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM watchlist WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie:  MovieEntity)

    @Query("UPDATE watchlist SET isWatchlist = :isWatchlist WHERE id = :movieId")
    suspend fun updateWatchlist(movieId: Int, isWatchlist: Boolean)

    @Query("UPDATE watchlist SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)

    @Query("DELETE FROM watchlist WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :movieId AND isWatchlist = 1)")
    fun isInWatchlist(movieId: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :movieId AND isFavorite = 1)")
    fun isInFavorite(movieId: Int): Flow<Boolean>
}