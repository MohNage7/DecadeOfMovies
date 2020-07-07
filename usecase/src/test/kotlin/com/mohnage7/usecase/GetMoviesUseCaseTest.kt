package com.mohnage7.usecase

import com.mohnage7.data.MoviesRepository
import com.mohnage7.domain.LocalMovie
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesUseCaseTest {

    private lateinit var SUT: GetMoviesUseCase
    @Mock
    private lateinit var repository: MoviesRepository

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = GetMoviesUseCase(repository)
    }

    @Test
    fun `given repository instance, when GetMoviesUseCase is invoked, then getMoviesFromDataSource() will be triggered`() {
        // action
        SUT()
        // assertion
        Mockito.verify(repository, Mockito.times(1)).getMoviesFromDataSource()
    }

    @Test
    fun `given repository instance, when GetMoviesUseCase is invoked multiple times, then getMoviesFromDataSource() will be triggered only one time`() {
        // prepare test
        val expectedData = getMockDataList()
        mockGetMoviesFromDataSourceCall(expectedData)
        // action - simulate multiple hits to the use case
        SUT.invoke()
        SUT.invoke()
        // assertion - getMoviesFromDataSource should be triggered only one time and the second time
        // the data will be fetched from the memory cache.
        Mockito.verify(repository, Mockito.times(1)).getMoviesFromDataSource()
    }

    @Test
    fun `given repository instance, when GetMoviesUseCase is invoked, then the expected data will be returned`() {
        // prepare test
        val expectedData = getMockDataList()
        mockGetMoviesFromDataSourceCall(expectedData)
        // action
        val actualData = SUT.invoke()
        // assertion
        assertEquals(
            actualData,
            expectedData
        )
    }

    private fun getMockDataList() = listOf(LocalMovie("", 2009, 1, listOf(), listOf()))

    private fun mockGetMoviesFromDataSourceCall(data: List<LocalMovie>) {
        Mockito.`when`(repository.getMoviesFromDataSource()).thenReturn(
            data
        )
    }
}