package com.mohnage7.usecase

import com.mohnage7.data.MoviePhotosRepository
import com.mohnage7.network.model.PhotosRequestConfig
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviePhotosUseCaseTest {

    private lateinit var SUT: GetMoviePhotosUseCase

    @Mock
    private lateinit var repository: MoviePhotosRepository

    @Before
    fun setup() {
        SUT = GetMoviePhotosUseCase(repository)
    }


    @Test
    fun `given repository instance, when GetMoviePhotosUseCase is invoked, then getMoviePhotosFromDataSource() will be triggered`() {
        // prepare test
        val config = PhotosRequestConfig("", "", 1, "", "", 1, 3)
        // action
        SUT(config)
        // assertion
        verify(repository, times(1)).getMoviePhotosFromDataSource(config)
    }

    @Test
    fun `given repository instance, when GetMoviePhotosUseCase is invoked, then the expected data will be returned`() {
        // prepare test
        val config = PhotosRequestConfig("", "", 1, "", "", 1, 3)
        val expectedData = getMockDataList()
        mockGetMoviesFromDataSourceCall(expectedData, config)
        // action
        val actualData = SUT.invoke(config)
        // assertion
        verify(repository, times(1)).getMoviePhotosFromDataSource(config)
        // check if the expected data and the actual data are the same
        assertEquals(
            actualData,
            expectedData
        )
    }

    private fun getMockDataList() = Single.just(listOf("url1", "url2"))

    private fun mockGetMoviesFromDataSourceCall(
        data: Single<List<String>>,
        config: PhotosRequestConfig
    ) {
        `when`(repository.getMoviePhotosFromDataSource(config)).thenReturn(
            data
        )
    }
}