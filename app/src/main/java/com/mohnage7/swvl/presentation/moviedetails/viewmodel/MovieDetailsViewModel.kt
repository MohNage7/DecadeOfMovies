package com.mohnage7.swvl.presentation.moviedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohnage7.network.PhotosRequestConfig
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.usecase.GetMoviePhotosUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val getMoviePhotosUseCase: GetMoviePhotosUseCase) :
    ViewModel() {

    private lateinit var disposable: Disposable
    private var photosLiveData = MutableLiveData<DataWrapper<List<String>>>()

    fun getMoviePhotos(config: PhotosRequestConfig) {
        disposable = getMoviePhotosUseCase(config)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { DataWrapper.loading(null) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> photosLiveData.value = DataWrapper.success(result) },
                { onError -> DataWrapper.error(getErrorMessage(onError), null) })
    }

    private fun getErrorMessage(it: Throwable): String {
        return "Error"

    }

    fun observePostsChanges(): LiveData<DataWrapper<List<String>>> {
        return photosLiveData
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}