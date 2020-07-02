package com.mohnage7.swvl.presentation.movies.view.callback

import com.mohnage7.swvl.presentation.movies.model.Movie


interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}
