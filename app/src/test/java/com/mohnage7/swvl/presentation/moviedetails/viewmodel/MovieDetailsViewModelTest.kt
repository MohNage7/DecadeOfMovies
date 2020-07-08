package com.mohnage7.swvl.presentation.moviedetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mohnage7.network.model.PhotosRequestConfig
import com.mohnage7.swvl.presentation.helpers.RxImmediateSchedulerRule
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.usecase.GetMoviePhotosUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @Mock
    lateinit var getMoviePhotosUseCase: GetMoviePhotosUseCase
    lateinit var SUT: MovieDetailsViewModel
    // required to handle RX Scheduler within the test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var dateObserver: Observer<DataWrapper<List<String>>>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        SUT = MovieDetailsViewModel(getMoviePhotosUseCase)

    }


    @Test
    fun `given getMoviePhotosUseCase instance, when getMoviePhotos is invoked, then getMoviePhotosUseCase will be triggered`() {
        // prepare test
        val config = getRequestConfig()
        val expectedData = getMoviePhotosUseCaseData()
        mockGetMoviesFromDataSourceCall(expectedData, config)

        // action
        SUT.getMoviePhotos(config)

        // assertion
        verify(getMoviePhotosUseCase, Mockito.times(1)).invoke(config)
    }

    @Test
    fun `given getMoviePhotosUseCase instance, when getMoviePhotos is invoked,then the expected data will be returned `() {
        // prepare test
        val config = getRequestConfig()
        val expectedData = DataWrapper.success(getMockList())
        mockGetMoviesFromDataSourceCall(getMoviePhotosUseCaseData(), config)
        SUT.observePostsChanges().observeForever(dateObserver)

        // action
        SUT.getMoviePhotos(config)

        // assertion
        assertEquals(
            SUT.observePostsChanges().value!!.data!!,
            expectedData.data
        )

        // release resources
        SUT.observePostsChanges().removeObserver(dateObserver)
    }


    private fun getRequestConfig() = PhotosRequestConfig("", "", 1, "", "", 1, 3)

    private fun getMoviePhotosUseCaseData() = Single.just(getMockList())

    private fun getMockList() = listOf("url1", "url2")

    private fun mockGetMoviesFromDataSourceCall(
        data: Single<List<String>>,
        config: PhotosRequestConfig
    ) {
        Mockito.`when`(getMoviePhotosUseCase(config)).thenReturn(
            data
        )
    }
}