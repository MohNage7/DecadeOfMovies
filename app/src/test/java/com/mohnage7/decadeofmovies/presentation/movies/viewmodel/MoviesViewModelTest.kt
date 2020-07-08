package com.mohnage7.decadeofmovies.presentation.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mohnage7.decadeofmovies.presentation.helpers.TestCoroutineRule
import com.mohnage7.decadeofmovies.presentation.helpers.getOrAwaitValue
import com.mohnage7.decadeofmovies.presentation.model.DataWrapper
import com.mohnage7.decadeofmovies.presentation.model.Movie
import com.mohnage7.domain.LocalMovie
import com.mohnage7.domain.SearchItem
import com.mohnage7.usecase.GetMoviesUseCase
import com.mohnage7.usecase.GetSearchMoviesUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var SUT: MoviesViewModel

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var searchMoviesUseCase: GetSearchMoviesUseCase

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @Before
    fun setup() {
        SUT = MoviesViewModel(getMoviesUseCase, searchMoviesUseCase)
    }


    @Test
    fun `given getMoviesUseCase  instance, when MoviesViewModel is initialized, then then loading will be emitted`() {
        testCoroutineRule.runBlockingTest {
            // action
            SUT.fetchMovies()
            val actualData = SUT.observeMoviesList().getOrAwaitValue()

            // assertion
            assertEquals(DataWrapper.Status.LOADING, actualData.status)
        }
    }


    @Test
    fun `given getMoviesUseCase  instance, when searchQuery is changed, then loading will be emitted`() {
        testCoroutineRule.runBlockingTest {
            // Action
            SUT.updateSearchQuery("the")
            val liveData = SUT.observeSearchMoviesList().getOrAwaitValue()

            // Assertion
            assertEquals(DataWrapper.Status.LOADING, liveData.status)
        }
    }

    // test case is unstable
//    @Test
//    fun `given getMoviesUseCase  instance, when searchQuery is changed, finally data will be emitted`() {
//        testCoroutineRule.runBlockingTest {
//            // prepare test
//            val data = getExpectedDataForSingleYear()
//            `when`(searchMoviesUseCase("the")).thenReturn(data)
//            val expectedDataWrapper = DataWrapper(DataWrapper.Status.SUCCESS, data, "")
//
//            // Action
//            SUT.updateSearchQuery("the")
//            // loading data that we can ignore
//            SUT.observeSearchMoviesList().getOrAwaitValue()
//            delay(500)
//            // actual data
//            val liveData = SUT.observeSearchMoviesList().getOrAwaitValue()
//
//            // Assertion
//            assertEquals(expectedDataWrapper.data, liveData.data)
//        }
//    }
//
//    private fun getExpectedDataForSingleYear(): List<SearchItem.ResultMovie> {
//        return listOf(
//            SearchItem.ResultMovie("the 2009_2", 2009, 2, listOf(), listOf()),
//            SearchItem.ResultMovie("the 2009_3", 2009, 3, listOf(), listOf()),
//            SearchItem.ResultMovie("the 2009_4", 2009, 4, listOf(), listOf()),
//            SearchItem.ResultMovie("the 2009_5", 2009, 5, listOf(), listOf()),
//            SearchItem.ResultMovie("the 2009_6", 2009, 5, listOf(), listOf())
//        )
//    }
}