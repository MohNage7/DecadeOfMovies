package com.mohnage7.usecase

import com.mohnage7.data.MoviePhotosRepository
import com.mohnage7.domain.Photo
import com.mohnage7.network.model.PhotosRequestConfig
import io.reactivex.Single


class GetMoviePhotosUseCase(private val repository: MoviePhotosRepository) {
    operator fun invoke(photosRequestConfig: PhotosRequestConfig): Single<List<String>> {
        return repository.getMoviePhotosFromDataSource(photosRequestConfig)
            .map { getPhotoURLFrom(it) }
    }


    private fun getPhotoURLFrom(photosList: List<Photo>): List<String> {
        return photosList.map { photo ->
            "https://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
        }
    }
}