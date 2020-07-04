package com.mohnage7.local

import io.reactivex.Single


interface PhotosLocalDataSource {
    fun getMoviePhotos(movieId: String): Single<List<String>>
    fun insertAll(movieId: String, photosList: List<String>)
}