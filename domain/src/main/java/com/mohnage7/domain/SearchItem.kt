package com.mohnage7.domain

sealed class SearchItem {
    data class Movie(
        val title: String? = null,
        val year: Int,
        val rating: Int? = null,
        private val genresList: List<String>? = null,
        private val castList: List<String>? = null
    ) : SearchItem()
    data class Category(val year: Int) : SearchItem()
}