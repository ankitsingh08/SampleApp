package ankit.com.sampleapp.domain.usecase

import ankit.com.sampleapp.domain.DomainTestData
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.UIResponseState
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException

/**
 * Created by AnkitSingh on 11/25/20.
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetRestaurantsUseCaseTest{

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Mock
    private lateinit var restaurantsRepository: RestaurantsRepository

    private lateinit var testCase: GetRestaurantsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        testCase = GetRestaurantsUseCase(restaurantsRepository)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun  `repository returns list of restaurants success scenario`()  = testCoroutineScope.runBlockingTest{
        val expectedResult =  flowOf(UIResponseState.Success(DomainTestData.getRestaurantsDomainData()))
        whenever(restaurantsRepository.getRestaurants()).thenReturn(expectedResult)

        val result = testCase.execute()

        assertEquals(expectedResult, result)
    }

    @Test
    fun  `return Error when repository throws an exception`()  = testCoroutineScope.runBlockingTest{
        val expectedError =  flowOf(UIResponseState.Error(IOException()))
        whenever(restaurantsRepository.getRestaurants()).thenReturn(expectedError)

        val error = testCase.execute()

        assertEquals(expectedError, error)
    }
}