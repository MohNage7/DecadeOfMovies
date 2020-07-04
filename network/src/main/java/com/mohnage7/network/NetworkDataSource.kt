package com.mohnage7.network

import com.mohnage7.network.model.PhotosResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkDataSource {

    @GET("?method=flickr.photos.search")
    fun getMoviePhotos(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: Int,
        @Query("text") movieName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<PhotosResponse>
}
