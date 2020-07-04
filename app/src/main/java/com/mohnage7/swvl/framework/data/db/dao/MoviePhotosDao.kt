package com.mohnage7.swvl.framework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mohnage7.swvl.framework.data.db.entity.PhotoEntity
import io.reactivex.Single

@Dao
interface MoviePhotosDao {
    @Insert
    fun insert(postsList: PhotoEntity)

    @Query("SELECT * from photos WHERE id = :movieId")
    fun getMoviePhotos(movieId: String): Single<PhotoEntity>
}
