package com.mohnage7.usecase

import com.mohnage7.domain.LocalMovie
import com.mohnage7.domain.SearchItem

class GetSearchMoviesUseCase(val getMoviesUseCase: GetMoviesUseCase) {
    operator fun invoke(searchQuery: String): List<SearchItem> {
        return getMoviesUseCase()
            // filter the list with any item that contains the query
            .filter { it.title.toLowerCase().contains(searchQuery.toLowerCase()) }
            .sortedByDescending { it.year }
            // will convert the list to a hashMap which has the year as a key and list of movies as value
            .groupBy { it.year }
            // map the stream to List of SearchItem
            .flatMap { entry ->
                listOf(
                    SearchItem.Category(entry.key),
                    //  * is spread operator "It passes the lists content to the listOf() function
                    *(getHighestNElements(5, entry.value)).toTypedArray()
                    // alternative way of sorting
                    //       *(sortListByRating(entry.value).takeLast(5)).toTypedArray()
                )
            }
    }

    /**
     * This method sorts the list by the comparing each elements rating
     * Takes the last n elements
     * Map the result to SearchItem.ResultMovie
     * @param elementsCount number of elements that we want our method to return
     * @param items the list that needs to be sorted
     */
    private fun getHighestNElements(
        elementsCount: Int,
        items: List<LocalMovie>
    ): List<SearchItem.ResultMovie> {
        return items.sortedBy { it.rating }
            .takeLast(elementsCount)
            .map { it.toResultMovie() }
    }

    /**
     * Manual Quick sort implementation
     * but I couldn't notice big difference in the performance.
     * So I used getHighestNElements as it looks cleaner
     */
    private fun sortListByRating(items: List<LocalMovie>): List<SearchItem.ResultMovie> {
        if (items.count() < 2) {
            return items.map { it.toResultMovie() }
        }
        val pivot = items[items.count() / 2].rating

        val equal = items.filter { it.rating == pivot }

        val less = items.filter { it.rating < pivot }

        val greater = items.filter { it.rating > pivot }

        return sortListByRating(less) + equal.map { it.toResultMovie() } + sortListByRating(greater)
    }
}