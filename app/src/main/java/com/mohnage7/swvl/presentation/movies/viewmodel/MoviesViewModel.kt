package com.mohnage7.swvl.presentation.movies.viewmodel

import androidx.lifecycle.ViewModel
import com.mohnage7.swvl.presentation.movies.model.Movie
import com.mohnage7.usecase.GetMoviesUseCase

class MoviesViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    fun observePostsChanges(): List<Movie> {
        return getMoviesUseCase().map {
            Movie(
                it.title,
                it.year,
                it.rating,
                it.genres,
                it.cast
            )
        }
    }
}