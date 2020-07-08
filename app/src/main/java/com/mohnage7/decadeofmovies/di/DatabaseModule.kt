package com.mohnage7.decadeofmovies.di

import androidx.room.Room
import com.mohnage7.decadeofmovies.framework.data.db.DATA_BASE_NAME
import com.mohnage7.decadeofmovies.framework.data.db.MoviesDatabase
import org.koin.dsl.module

val dataBaseModule = module {
    single { Room.databaseBuilder(get(), MoviesDatabase::class.java, DATA_BASE_NAME).build() }
    single { get<MoviesDatabase>().photosDao }
}