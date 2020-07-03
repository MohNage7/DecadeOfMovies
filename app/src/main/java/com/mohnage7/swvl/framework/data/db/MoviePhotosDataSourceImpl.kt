package com.mohnage7.swvl.framework.data.db

import com.mohnage7.domain.Photo
import com.mohnage7.local.PhotosLocalDataSource
import com.mohnage7.swvl.framework.data.db.dao.MoviePhotosDao
import com.mohnage7.swvl.framework.data.db.entity.PhotoEntity
import io.reactivex.Single

class MoviePhotosDataSourceImpl(private val dao: MoviePhotosDao) : PhotosLocalDataSource {

    override fun getMoviePhotos(): Single<List<Photo>> {
        return dao.getMoviePhotos().flatMap {
            Single.just(it.map { photoEntity ->
                Photo(
                    photoEntity.farm,
                    photoEntity.server,
                    photoEntity.id,
                    photoEntity.secret
                )
            })
        }
    }

    override fun insertAll(photosList: List<Photo>) {
        dao.insertAll(photosList.map { photo ->
            PhotoEntity(
                photo.id,
                photo.farm,
                photo.server,
                photo.secret
            )
        })
    }

}