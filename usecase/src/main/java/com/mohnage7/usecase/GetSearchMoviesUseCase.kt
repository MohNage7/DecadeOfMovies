package com.mohnage7.usecase

import com.mohnage7.domain.SearchItem

class GetSearchMoviesUseCase(val getMoviesUseCase: GetMoviesUseCase) {
    operator fun invoke(searchQuery: String): List<SearchItem> {
        return getMoviesUseCase()
            .filter { it.title.toLowerCase().contains(searchQuery.toLowerCase()) }
            // will convert the list to a hashMap which has the year as a key and list of movies as value
            .groupBy { it.year }
            // map the stream to List of SearchItem
            .flatMap {
                listOf(SearchItem.Category(it.key), *(it.value.map { movie ->
                    (SearchItem.Movie(
                        movie.title,
                        movie.year,
                        movie.rating,
                        movie.genres,
                        movie.cast
                    ))
                }).toTypedArray())
            }
    }
}