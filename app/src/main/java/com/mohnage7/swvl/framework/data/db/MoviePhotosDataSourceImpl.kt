package com.mohnage7.swvl.framework.data.db

import com.mohnage7.local.PhotosLocalDataSource
import com.mohnage7.swvl.framework.data.db.dao.MoviePhotosDao
import com.mohnage7.swvl.framework.data.db.entity.PhotoEntity
import io.reactivex.Single

class MoviePhotosDataSourceImpl(private val dao: MoviePhotosDao) : PhotosLocalDataSource {

    override fun getMoviePhotos(movieId: String): Single<List<String>> {
        return dao.getMoviePhotos(movieId).flatMap {
            Single.just(it.photosList)
        }
    }

    override fun insertAll(movieId: String, photosList: List<String>) {
        dao.insert(PhotoEntity(movieId, photosList))
    }
}