package com.mohnage7.swvl.presentation.movies.viewmodel

import androidx.lifecycle.*
import com.mohnage7.domain.SearchItem
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.usecase.GetMoviesUseCase
import com.mohnage7.usecase.GetSearchMoviesUseCase
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: GetSearchMoviesUseCase
) : ViewModel() {
    private val moviesMutableLiveData: LiveData<DataWrapper<List<Movie>>>
    private val searchQuery = MutableLiveData<String>()
    private val searchMoviesList: LiveData<DataWrapper<List<SearchItem>>>
    private lateinit var moviesList: List<Movie>

    /**
     * As soon as MoviesViewModel is initialized we will emit a loading object to show loading on the screen
     * Then fetch the movies list from the json file and emit it to the view to be displayed.
     */
    init {
        moviesMutableLiveData =
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(loadingDataWrapper())
                emit(moviesDataWrapper())
            }
        searchMoviesList =
            Transformations.switchMap(searchQuery) { query ->
                liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                    emit(searchLoading())
                    emit(searchMoviesDataWrapper(query))
                }
            }
    }

    private fun loadingDataWrapper(): DataWrapper<List<Movie>> {
        return DataWrapper.loading()
    }

    private fun searchLoading(): DataWrapper<List<SearchItem>> {
        return DataWrapper.loading()
    }

    private fun moviesDataWrapper(): DataWrapper<List<Movie>> {
        return DataWrapper.success(getMovies())
    }

    private fun searchMoviesDataWrapper(query: String): DataWrapper<List<SearchItem>> {
        return DataWrapper.success(getSearchMovies(query))
    }

    private fun getSearchMovies(query: String): List<SearchItem> {
        return searchMoviesUseCase(query)
    }

    /**
     * This method fetches the local movies list from the file and maps LocalMovie into Movie
     * to be used on the presentation layer.
     * Also it caches the list in the memory to be used later for searching the list
     * @return list of movies to be displayed.
     */
    private fun getMovies(): List<Movie> {
        moviesList = getMoviesUseCase().map {
            Movie(
                it.title,
                it.year,
                it.rating,
                it.genres,
                it.cast
            )
        }
        return moviesList
    }

    /**
     * Any subscriber on this LiveData object will get loading and data updates
     */
    fun observeMoviesList(): LiveData<DataWrapper<List<Movie>>> {
        return moviesMutableLiveData
    }

    /**
     * Any subscriber on this LiveData object will get loading and data updates
     */
    fun observeSearchMoviesList(): LiveData<DataWrapper<List<SearchItem>>> {
        return searchMoviesList
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}