package com.mohnage7.usecase

import com.mohnage7.data.MoviesRepository
import com.mohnage7.domain.LocalMovie

class GetMoviesUseCase(private val repository: MoviesRepository) {

    private var moviesList: List<LocalMovie>? = null

    operator fun invoke(): List<LocalMovie> {
        if (moviesList.isNullOrEmpty()) {
            moviesList = repository.getMoviesFromDataSource().sortedBy { it.title }
        }
        return moviesList as List<LocalMovie>
    }
}