package com.mohnage7.swvl.di

import com.mohnage7.data.MoviesRepository
import com.mohnage7.swvl.framework.data.file.MoviesLocalDataSourceImpl
import com.mohnage7.swvl.presentation.movies.viewmodel.MoviesViewModel
import com.mohnage7.usecase.GetMoviesUseCase
import com.mohnage7.usecase.GetSearchMoviesUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val moviesModule = module {
    single { MoviesLocalDataSourceImpl(get()) }
    single { MoviesRepository(get<MoviesLocalDataSourceImpl>()) }
    single { GetMoviesUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    viewModel { MoviesViewModel(get(),get()) }
}