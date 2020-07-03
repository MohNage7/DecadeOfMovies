package com.mohnage7.swvl.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mohnage7.network.NetworkDataSource
import com.mohnage7.swvl.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val CONNECTION_TIMEOUT = 10 // 10 seconds
private const val READ_TIMEOUT = 2 // 2 seconds
private const val WRITE_TIMEOUT = 2 // 2 seconds

val networkModule = module {
    single { providesHttpLogging() }
    single { providesHttpClient(get()) }
    single { providesRetrofit(get()) }
    single { provideRestService(get()) }
}

internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        //converts Retrofit response into Observable
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


internal fun providesHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    // add request time out
    builder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    builder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
    builder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
    // Add logging into
    builder.interceptors().add(httpLoggingInterceptor)
    builder.addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        val request = requestBuilder.build()
        chain.proceed(request)
    }
    return builder.build()
}

internal fun providesHttpLogging(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}


internal fun provideRestService(retrofit: Retrofit): NetworkDataSource {
    return retrofit.create(NetworkDataSource::class.java)
}

