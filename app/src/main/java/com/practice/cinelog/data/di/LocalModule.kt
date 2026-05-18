package com.practice.cinelog.data.di

import android.content.Context
import androidx.room.Room
import com.practice.cinelog.data.local.db.CineLogDatabase
import com.practice.cinelog.data.local.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CineLogDatabase =
        Room.databaseBuilder(
            context,
            CineLogDatabase::class.java,
            "cinelog.db"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(database: CineLogDatabase): MovieDao =
        database.movieDao()
}