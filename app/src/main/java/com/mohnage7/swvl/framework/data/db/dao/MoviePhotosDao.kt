package com.mohnage7.swvl.framework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mohnage7.domain.Photo
import com.mohnage7.swvl.framework.data.db.entity.PhotoEntity
import io.reactivex.Single

@Dao
interface MoviePhotosDao {
    @Insert
    fun insertAll(postsList: List<PhotoEntity>)

    @Query("SELECT * from photos")
    fun getMoviePhotos(): Single<List<PhotoEntity>>
}
