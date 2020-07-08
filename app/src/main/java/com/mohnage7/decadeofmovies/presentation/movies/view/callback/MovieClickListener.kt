package com.mohnage7.decadeofmovies.presentation.movies.view.callback

import com.mohnage7.decadeofmovies.presentation.model.Movie


interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}
