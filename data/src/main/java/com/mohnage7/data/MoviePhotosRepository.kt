package com.mohnage7.data

import com.mohnage7.domain.Photo
import com.mohnage7.local.PhotosLocalDataSource
import com.mohnage7.network.NetworkDataSource
import com.mohnage7.network.PhotosRequestConfig
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoviePhotosRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: PhotosLocalDataSource
) {
    fun getMoviePhotosFromDataSource(photosRequestConfig: PhotosRequestConfig): Single<List<Photo>> {
        return localDataSource.getMoviePhotos()
            .filter { it.isNotEmpty() }
            .switchIfEmpty(networkDataSource.getMoviePhotos(
                photosRequestConfig.apiKey,
                photosRequestConfig.format,
                photosRequestConfig.noJsonCallback,
                photosRequestConfig.movieName,
                photosRequestConfig.page,
                photosRequestConfig.perPage
            ).subscribeOn(Schedulers.io()).flatMap {
                Single.just(it.photos)
            }.doOnSuccess { photosList ->
                photosList?.let {
                    localDataSource.insertAll(it)
                }
            }).subscribeOn(Schedulers.io())
    }
}