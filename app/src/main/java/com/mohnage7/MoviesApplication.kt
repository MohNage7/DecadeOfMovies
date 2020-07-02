package com.mohnage7

import android.app.Application
import com.mohnage7.swvl.di.moviesModule
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
            modules(listOf(moviesModule))
        }
    }
}