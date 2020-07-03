package com.mohnage7.swvl.framework.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohnage7.swvl.framework.data.db.dao.MoviePhotosDao
import com.mohnage7.swvl.framework.data.db.entity.PhotoEntity

const val DATA_BASE_NAME = "movies_db"

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val photosDao: MoviePhotosDao
}