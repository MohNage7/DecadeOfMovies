package com.mohnage7.local

import com.mohnage7.domain.LocalMovie


interface MoviesLocalDataSource {
    fun getMovies(): List<LocalMovie>
}