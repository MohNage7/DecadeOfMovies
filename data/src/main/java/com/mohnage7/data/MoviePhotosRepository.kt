package com.mohnage7.data

import com.mohnage7.domain.Photo
import com.mohnage7.local.PhotosLocalDataSource
import com.mohnage7.network.NetworkDataSource
import com.mohnage7.network.model.PhotosRequestConfig
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoviePhotosRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: PhotosLocalDataSource
) {
    /**
     * This method do the following:
     * - Try to fetch the data from the database. If it fails then try to fetch it from network
     * - The response that is
     */
    fun getMoviePhotosFromDataSource(photosRequestConfig: PhotosRequestConfig): Single<List<String>> {
        return localDataSource.getMoviePhotos(photosRequestConfig.movieId)
            .onErrorResumeNext(networkDataSource.getMoviePhotos(
                photosRequestConfig.apiKey,
                photosRequestConfig.format,
                photosRequestConfig.noJsonCallback,
                photosRequestConfig.movieName,
                photosRequestConfig.page,
                photosRequestConfig.perPage
            ).subscribeOn(Schedulers.io()).flatMap {
                Single.just(getListOfURLFrom(it.photosBody.photosList))
            }.doOnSuccess { photosList ->
                photosList?.let {
                    localDataSource.insertAll(photosRequestConfig.movieId, it)
                }
            }).subscribeOn(Schedulers.io())
    }


    private fun getListOfURLFrom(photosList: List<Photo>): List<String> {
        return photosList.map { photo -> photo.getPhotoURL() }
    }
}