package ankit.com.sampleapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ankit.com.sampleapp.data.database.RestaurantsDao
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.entity.SortingValues
import ankit.com.sampleapp.data.entity.Status
import ankit.com.sampleapp.data.entity.toDomainModel
import ankit.com.sampleapp.data.mapper.toRestaurantsDomainList
import ankit.com.sampleapp.domain.DomainTestData
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.util.UIResponseState
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by AnkitSingh on 11/29/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RestaurantsRepositoryImplTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var restaurantsDataSource: RestaurantsDataSource

    @Mock
    private lateinit var restaurantsDao: RestaurantsDao

    private lateinit var restaurantsRepositoryImpl: RestaurantsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        restaurantsRepositoryImpl = RestaurantsRepositoryImpl(restaurantsDataSource, restaurantsDao)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun `get restaurants return list of restaurants success scenario`()  = testCoroutineScope.runBlockingTest {
        val testData = DomainTestData.getRestaurantData()
        val expectedOutput = UIResponseState.Success(testData.toRestaurantsDomainList())
        whenever(restaurantsDao.getRestaurants()).thenReturn(testData)

        val flow = restaurantsRepositoryImpl.getRestaurants()

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get restaurants return list return Error scenario`()  = testCoroutineScope.runBlockingTest {
        val exception = NullPointerException()
        val expectedOutput = UIResponseState.Error(exception)
        whenever(restaurantsDao.getRestaurants()).thenThrow(exception)

        val flow = restaurantsRepositoryImpl.getRestaurants()

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(expectedOutput, data)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by distance`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1536.0, 96.0, 5000.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.OPEN, false)
        val sortingValues2 = SortingValues(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(1536.0, 96.0, 200.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Greek Express", Status.CLOSE, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)

        //Expected result after sorting restaurant2 will come before restaurant1 as its closer and both restaurants are open
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("distance")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by minimum cost`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1536.0, 96.0, 5000.0, 200.0, 17.0, 96.0, 3000.0, 4.5)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.OPEN, false)
        val sortingValues2 = SortingValues(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(1536.0, 96.0, 200.0, 200.0, 17.0, 96.0, 5000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Greek Express", Status.CLOSE, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        restaurantList.add(restaurant3)

        //Expected result after sorting restaurant2 will come before restaurant1 as its has min cost compared to restaurant1 and both restaurants are open
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        sortedOutput.add(restaurant3.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("minCost")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by average product price`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1000.0, 96.0, 5000.0, 200.0, 17.0, 96.0, 3000.0, 4.5)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.OPEN, false)
        val sortingValues2 = SortingValues(500.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(200.0, 96.0, 200.0, 200.0, 17.0, 96.0, 5000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Tandori Express", Status.OPEN, false)
        val sortingValues4 = SortingValues(200.0, 96.0, 200.0, 200.0, 17.0, 96.0, 5000.0, 4.5)
        val restaurant4 = Restaurant(sortingValues4, "Greek Express", Status.CLOSE, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        restaurantList.add(restaurant3)
        restaurantList.add(restaurant4)

        //Expected result after sorting restaurant3 has lowest average product price and all restaurants are in open status
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant3.toDomainModel())
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        sortedOutput.add(restaurant4.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("averageProductPrice")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by deliver cost`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1000.0, 96.0, 5000.0, 100.0, 17.0, 96.0, 3000.0, 4.5)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.CLOSE, false)
        val sortingValues2 = SortingValues(500.0, 96.0, 1190.0, 500.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(200.0, 96.0, 200.0, 300.0, 17.0, 96.0, 5000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Greek Express", Status.OPEN, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        restaurantList.add(restaurant3)

        //Expected result after sorting restaurant3 has minimum deliver cost in open restaurants and restaurant one delivery cose is less but its status is closed
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant3.toDomainModel())
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("deliveryCosts")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by Rating average`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1000.0, 96.0, 5000.0, 100.0, 17.0, 96.0, 3000.0, 4.2)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.CLOSE, false)
        val sortingValues2 = SortingValues(500.0, 96.0, 1190.0, 500.0, 17.0, 96.0, 1000.0, 5.0)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(200.0, 96.0, 200.0, 300.0, 17.0, 96.0, 5000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Greek Express", Status.OPEN, false)
        val sortingValues4 = SortingValues(200.0, 96.0, 200.0, 300.0, 17.0, 96.0, 5000.0, 4.0)
        val restaurant4 = Restaurant(sortingValues4, "MacD", Status.ORDERAHEAD, false)
        val sortingValues5 = SortingValues(200.0, 96.0, 200.0, 300.0, 17.0, 96.0, 5000.0, 4.0)
        val restaurant5 = Restaurant(sortingValues5, "Olive Garden", Status.CLOSE, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        restaurantList.add(restaurant3)
        restaurantList.add(restaurant4)
        restaurantList.add(restaurant5)

        //Expected result after sorting restaurant3 has highest rating
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant3.toDomainModel())
        sortedOutput.add(restaurant4.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        sortedOutput.add(restaurant5.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("ratingAverage")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }

    @Test
    fun `get sorted restaurants return list of restaurants sorted by Poularity`()  = testCoroutineScope.runBlockingTest {
        //unsorted data returned from dao
        val sortingValues1 = SortingValues(1000.0, 96.0, 5000.0, 100.0, 50.0, 96.0, 3000.0, 4.2)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.CLOSE, false)
        val sortingValues2 = SortingValues(500.0, 96.0, 1190.0, 500.0, 70.0, 96.0, 1000.0, 5.0)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val sortingValues3 = SortingValues(200.0, 96.0, 200.0, 300.0, 100.0, 96.0, 5000.0, 4.5)
        val restaurant3 = Restaurant(sortingValues3, "Greek Express", Status.OPEN, false)
        val sortingValues4 = SortingValues(200.0, 96.0, 200.0, 300.0, 80.0, 96.0, 5000.0, 4.0)
        val restaurant4 = Restaurant(sortingValues4, "MacD", Status.ORDERAHEAD, false)
        val sortingValues5 = SortingValues(200.0, 96.0, 200.0, 300.0, 200.0, 96.0, 5000.0, 4.0)
        val restaurant5 = Restaurant(sortingValues5, "Olive Garden", Status.CLOSE, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        restaurantList.add(restaurant3)
        restaurantList.add(restaurant4)
        restaurantList.add(restaurant5)

        //Expected result after sorting restaurant3 has highest poularity and its open
        val sortedOutput = mutableListOf<RestaurantDomainModel>()
        sortedOutput.add(restaurant3.toDomainModel())
        sortedOutput.add(restaurant2.toDomainModel())
        sortedOutput.add(restaurant4.toDomainModel())
        sortedOutput.add(restaurant5.toDomainModel())
        sortedOutput.add(restaurant1.toDomainModel())
        val expectedOutput = UIResponseState.Success(sortedOutput)
        whenever(restaurantsDao.getRestaurants()).thenReturn(restaurantList)

        val flow = restaurantsRepositoryImpl.getSortedRestaurantsList("popularity")

        // Verify
        flow.collect{ data ->
            Assert.assertEquals(data, expectedOutput)
        }
    }
}