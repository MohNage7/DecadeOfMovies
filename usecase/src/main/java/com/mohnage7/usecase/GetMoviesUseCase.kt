package com.mohnage7.usecase

import com.mohnage7.data.MoviesRepository
import com.mohnage7.domain.LocalMovie

class GetMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): List<LocalMovie> = repository.getMoviesFromDataSource().sortedBy { it.title }
}