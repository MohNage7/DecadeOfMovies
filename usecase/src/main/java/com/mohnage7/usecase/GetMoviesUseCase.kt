package com.mohnage7.usecase

import com.mohnage7.data.MoviesRepository
import com.mohnage7.domain.LocalMovie

class GetMoviesUseCase(private val repository: MoviesRepository) {
    // cache the list in memory for future usage.
    private var moviesList: List<LocalMovie>? = null

    operator fun invoke(): List<LocalMovie> {
        if (moviesList.isNullOrEmpty()) {
            moviesList = repository.getMoviesFromDataSource()
        }
        return moviesList as List<LocalMovie>
    }
}