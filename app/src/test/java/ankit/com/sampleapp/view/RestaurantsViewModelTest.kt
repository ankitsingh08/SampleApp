package ankit.com.sampleapp.view

import androidx.lifecycle.Observer
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.domain.DomainTestData
import ankit.com.sampleapp.domain.usecase.GetRestaurantsUseCase
import ankit.com.sampleapp.util.UIResponseState
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by AnkitSingh on 11/25/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RestaurantsViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Mock
    private lateinit var restaurantsUseCase: GetRestaurantsUseCase

    @Mock
    private lateinit var restaurantsListObserver: Observer<List<Restaurant>>

    private lateinit var viewModel: RestaurantsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = RestaurantsViewModel(restaurantsUseCase)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

//    @Test
//    fun `get restaurants usecase returns list of restaurants success scenario`() = testCoroutineScope.runBlockingTest {
//        // when
//        val testData = DomainTestData.getRestaurantsDummyData()
//        val testFlow = flow {
//            delay(100)
//            emit(UIResponseState.Success(testData.t))
//        }
//
//        whenever(restaurantsUseCase.execute()).thenReturn(testFlow)
//
//        viewModel.getRestaurantsData()
//        viewModel.restaurants.observeForever(restaurantsListObserver)
//
//        verify(restaurantsUseCase).execute()
//        verify(restaurantsListObserver).onChanged(testData)
//        advanceTimeBy(100)
//
//        viewModel.restaurants.removeObserver(restaurantsListObserver)
//    }
 }