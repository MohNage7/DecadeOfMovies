package com.mohnage7.domain

sealed class SearchItem {
    data class ResultMovie(
        val title: String,
        val year: Int,
        val rating: Int,
        val genresList: List<String>? = null,
        val castList: List<String>? = null
    ) : SearchItem()

    data class Category(val year: Int) : SearchItem()
}