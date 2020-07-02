package com.mohnage7.swvl.presentation.movies.model

class Movie(
    val title: String,
    val year: String,
    val genresList: List<String>,
    val castList: List<String>
) {
    fun getGenresAsString(): CharSequence? {
        return genresList.joinToString(separator = ", ")
    }
}