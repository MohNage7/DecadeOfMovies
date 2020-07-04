package com.mohnage7.local

import com.mohnage7.domain.Photo
import io.reactivex.Single


interface PhotosLocalDataSource {
    fun getMoviePhotos(movieName: String): Single<List<Photo>>
    fun insertAll(photosList: List<Photo>)
}