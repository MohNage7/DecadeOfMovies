package com.mohnage7.data

import com.mohnage7.domain.LocalMovie
import com.mohnage7.local.MoviesLocalDataSource

class MoviesRepository(private val dataSource: MoviesLocalDataSource) {
    fun getMovies(): List<LocalMovie> {
        return dataSource.getMovies()
    }
}