package com.practice.cinelog.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CineLogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}