package com.mohnage7.usecase

import com.mohnage7.domain.LocalMovie
import com.mohnage7.domain.SearchItem
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSearchMoviesUseCaseTest {

    private lateinit var SUT: GetSearchMoviesUseCase

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setup() {
        SUT = GetSearchMoviesUseCase(getMoviesUseCase)
    }

    @Test
    fun `given getMoviesUseCase instance, when GetSearchMoviesUseCase is invoked with existed query, then the expected data will be returned`() {
        // prepare test
        `when`(getMoviesUseCase()).thenReturn(getMockDataList())
        val expectedData = getExpectedData()

        // action
        val actualData = SUT.invoke("the")

        // assertion
        assertEquals(
            actualData,
            expectedData
        )
    }

    @Test
    fun `given getMoviesUseCase instance, when GetSearchMoviesUseCase is invoked with non-existing query, then empty list will be returned`() {
        // prepare test
        `when`(getMoviesUseCase()).thenReturn(getMockDataList())

        // action
        val actualData = SUT.invoke("hi")

        // assertion
        assertEquals(
            actualData,
            listOf<SearchItem>()
        )
    }

    @Test
    fun `given getMoviesUseCase instance, when getHighestNElements is invoked with a list, then it should return 5 movies with the highest rating `() {
        // prepare test
        val moviesList = getMockDataForSingleYear()
        val expectedData = getExpectedDataForSingleYear()
        // action
        val actualData = SUT.getHighestNElements(5, moviesList)

        // assertion
        assertEquals(
            actualData,
            expectedData
        )
    }

    private fun getExpectedData(): List<SearchItem> {
        return listOf(
            SearchItem.Category(2010),
            SearchItem.ResultMovie("the 2010_5", 2010, 1, listOf(), listOf()),
            SearchItem.ResultMovie("the 2010_3", 2010, 2, listOf(), listOf()),
            SearchItem.ResultMovie("the 2010_6", 2010, 2, listOf(), listOf()),
            SearchItem.ResultMovie("the 2010_2", 2010, 3, listOf(), listOf()),
            SearchItem.ResultMovie("the 2010_1", 2010, 4, listOf(), listOf()),
            SearchItem.Category(2009),
            // reuse the method to avoid code redundancy
            *getExpectedDataForSingleYear().toTypedArray()
        )
    }

    private fun getExpectedDataForSingleYear(): List<SearchItem.ResultMovie> {
        return listOf(
            SearchItem.ResultMovie("the 2009_2", 2009, 2, listOf(), listOf()),
            SearchItem.ResultMovie("the 2009_3", 2009, 3, listOf(), listOf()),
            SearchItem.ResultMovie("the 2009_4", 2009, 4, listOf(), listOf()),
            SearchItem.ResultMovie("the 2009_5", 2009, 5, listOf(), listOf()),
            SearchItem.ResultMovie("the 2009_6", 2009, 5, listOf(), listOf())
        )
    }

    private fun getMockDataForSingleYear() = listOf(
        LocalMovie("the 2009_1", 2009, 1, listOf(), listOf()),
        LocalMovie("the 2009_2", 2009, 2, listOf(), listOf()),
        LocalMovie("the 2009_3", 2009, 3, listOf(), listOf()),
        LocalMovie("the 2009_4", 2009, 4, listOf(), listOf()),
        LocalMovie("the 2009_5", 2009, 5, listOf(), listOf()),
        LocalMovie("the 2009_6", 2009, 5, listOf(), listOf())
    )

    private fun getMockDataList() = listOf(
        // reuse the method to avoid code redundancy
        *getMockDataForSingleYear().toTypedArray(),
        LocalMovie("the 2010_1", 2010, 4, listOf(), listOf()),
        LocalMovie("the 2010_2", 2010, 3, listOf(), listOf()),
        LocalMovie("the 2010_3", 2010, 2, listOf(), listOf()),
        LocalMovie("the 2010_4", 2010, 1, listOf(), listOf()),
        LocalMovie("the 2010_5", 2010, 1, listOf(), listOf()),
        LocalMovie("the 2010_6", 2010, 2, listOf(), listOf()),

        LocalMovie("2011_1", 2011, 3, listOf(), listOf()),
        LocalMovie("2011_2", 2011, 4, listOf(), listOf()),
        LocalMovie("2011_3", 2011, 5, listOf(), listOf())
    )
}