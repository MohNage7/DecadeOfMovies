package com.mohnage7.network

class PhotosRequestConfig(
    val apiKey: String,
    val format: String = "json",
    val noJsonCallback: Int = 1,
    val movieName: String,
    val page: Int = 1,
    val perPage: Int = 3
)