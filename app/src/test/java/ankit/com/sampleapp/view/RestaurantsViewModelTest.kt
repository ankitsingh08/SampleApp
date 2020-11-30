package ankit.com.sampleapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ankit.com.sampleapp.domain.DomainTestData
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.usecase.AddToFavoritesUseCase
import ankit.com.sampleapp.domain.usecase.FilterRestaurantsUseCase
import ankit.com.sampleapp.domain.usecase.GetRestaurantsUseCase
import ankit.com.sampleapp.util.UIResponseState
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
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

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var restaurantsUseCase: GetRestaurantsUseCase

    @Mock
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase

    @Mock
    private lateinit var filterRestaurantsUseCase: FilterRestaurantsUseCase

    @Mock
    private lateinit var restaurantsListObserver: Observer<List<RestaurantDomainModel>>

    private lateinit var viewModel: RestaurantsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = RestaurantsViewModel(restaurantsUseCase, addToFavoritesUseCase, filterRestaurantsUseCase)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun `get restaurants usecase returns list of restaurants success scenario`() = testCoroutineScope.runBlockingTest {
        // when
        val testData = DomainTestData.getRestaurantsDomainData()
        whenever(restaurantsUseCase.execute()).thenReturn(flowOf(UIResponseState.Success(testData)))

        viewModel.getRestaurantsData()
        viewModel.restaurants.observeForever(restaurantsListObserver)

        verify(restaurantsUseCase).execute()
        verify(restaurantsListObserver).onChanged(testData)

        viewModel.restaurants.removeObserver(restaurantsListObserver)
    }

    @Test
    fun `get restaurants usecase returns Error scenario`() = testCoroutineScope.runBlockingTest {
        // when
        val exception = Exception()
        whenever(restaurantsUseCase.execute()).thenReturn(flowOf(UIResponseState.Error(exception)))

        viewModel.getRestaurantsData()
        viewModel.restaurants.observeForever(restaurantsListObserver)

        verify(restaurantsUseCase).execute()
        verify(restaurantsListObserver).onChanged(emptyList())

        viewModel.restaurants.removeObserver(restaurantsListObserver)
    }

    @Test
    fun `add to favorites add restaurant successfully and returns success response`() = testCoroutineScope.runBlockingTest {
        val testData = DomainTestData.getRestaurantsWithFavoritesDummyData()
        val testRestaurantName = "De Amsterdamsche Tram"
        whenever(addToFavoritesUseCase.execute(testRestaurantName, true)).thenReturn(flowOf(UIResponseState.Success(testData)))

        viewModel.addToFavorites(testRestaurantName, true)
        viewModel.restaurants.observeForever(restaurantsListObserver)

        verify(addToFavoritesUseCase).execute(testRestaurantName, true)
        verify(restaurantsListObserver).onChanged(testData)

        viewModel.restaurants.removeObserver(restaurantsListObserver)
    }

    @Test
    fun `add to favorites returns Error response`() = testCoroutineScope.runBlockingTest {
        val testRestaurantName = "De Amsterdamsche Tram"
        val exception = Exception()
        whenever(addToFavoritesUseCase.execute(testRestaurantName, true)).thenReturn(flowOf(UIResponseState.Error(exception)))

        viewModel.addToFavorites(testRestaurantName, true)
        viewModel.restaurants.observeForever(restaurantsListObserver)

        verify(addToFavoritesUseCase).execute(testRestaurantName, true)
        verify(restaurantsListObserver).onChanged(emptyList())

        viewModel.restaurants.removeObserver(restaurantsListObserver)
    }
 }