package com.mohnage7.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkDataSource {
    /**
     * https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key={
    YOUR_API_KEY}&format=json&nojsoncallback=1&text={MOVIE_TITLE}&page=1&per_pa
    ge=10
     */

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
