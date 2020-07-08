package com.mohnage7.decadeofmovies.di

import com.mohnage7.data.MoviePhotosRepository
import com.mohnage7.decadeofmovies.framework.data.db.MoviePhotosDataSourceImpl
import com.mohnage7.decadeofmovies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.mohnage7.usecase.GetMoviePhotosUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val movieDetailModule = module {
    single { MoviePhotosDataSourceImpl(get()) }
    single { MoviePhotosRepository(get(), get<MoviePhotosDataSourceImpl>()) }
    single { GetMoviePhotosUseCase(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}