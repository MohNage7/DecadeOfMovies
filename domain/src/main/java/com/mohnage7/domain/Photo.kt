package com.mohnage7.domain

class Photo(
    private val farm: String? = null,
    private val server: String? = null,
    val id: String,
    val title: String? = null,
    val secret: String? = null
)
{
    fun getPhotoURL(): String {
        return "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"
    }
}