package com.mohnage7.swvl.di

import com.mohnage7.data.MoviePhotosRepository
import com.mohnage7.data.MoviesRepository
import com.mohnage7.swvl.framework.data.db.MoviePhotosDataSourceImpl
import com.mohnage7.swvl.framework.data.file.MoviesLocalDataSourceImpl
import com.mohnage7.swvl.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.mohnage7.swvl.presentation.movies.viewmodel.MoviesViewModel
import com.mohnage7.usecase.GetMoviePhotosUseCase
import com.mohnage7.usecase.GetMoviesUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val movieDetailModule = module {
    single { MoviePhotosDataSourceImpl(get()) }
    single { MoviePhotosRepository(get(), get<MoviePhotosDataSourceImpl>()) }
    single { GetMoviePhotosUseCase(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}