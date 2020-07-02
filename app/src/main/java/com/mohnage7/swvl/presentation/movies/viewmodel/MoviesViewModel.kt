package com.mohnage7.swvl.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.usecase.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    private val moviesMutableLiveData: LiveData<DataWrapper<List<Movie>>>

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
    }

    private fun loadingDataWrapper(): DataWrapper<List<Movie>> {
        return DataWrapper.loading()
    }

    private fun moviesDataWrapper(): DataWrapper<List<Movie>> {
        return DataWrapper.success(getMovies())
    }

    /**
     * This method fetches the local movies list from the file and maps LocalMovie into Movie
     * to be used on the presentation layer.
     * @return list of movies to be displayed.
     */
    private fun getMovies(): List<Movie> {
        return getMoviesUseCase().map {
            Movie(
                it.title,
                it.year,
                it.rating,
                it.genres,
                it.cast
            )
        }
    }

    /**
     * Any subscriber on this LiveData object will get loading and data updates
     */
    fun observeMoviesList(): LiveData<DataWrapper<List<Movie>>> {
        return moviesMutableLiveData
    }

}