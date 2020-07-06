package com.mohnage7.domain


class LocalMovie(
    val title: String,
    val year: Int,
    val rating: Int ,
    val genres: List<String>? = null,
    val cast: List<String>? = null
)
{
    fun toResultMovie(): SearchItem.ResultMovie {
        return SearchItem.ResultMovie(
            title,
            year,
            rating,
            genres,
            cast
        )
    }
}