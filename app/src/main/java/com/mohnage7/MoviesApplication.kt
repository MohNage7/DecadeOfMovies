package com.mohnage7

import android.app.Application
import com.mohnage7.decadeofmovies.di.dataBaseModule
import com.mohnage7.decadeofmovies.di.movieDetailModule
import com.mohnage7.decadeofmovies.di.moviesModule
import com.mohnage7.decadeofmovies.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoinInjection()
    }

    private fun startKoinInjection() {
        startKoin {
            androidContext(this@MoviesApplication)
            modules(
                listOf(
                    moviesModule,
                    dataBaseModule,
                    movieDetailModule,
                    networkModule
                )
            )
        }
    }
}