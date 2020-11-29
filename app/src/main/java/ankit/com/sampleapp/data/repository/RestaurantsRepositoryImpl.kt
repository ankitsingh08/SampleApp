package ankit.com.sampleapp.data.repository

import ankit.com.sampleapp.data.database.RestaurantsDao
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.mapper.toRestaurantsDomainList
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.Constants.SORT_BY_AVERAGE_PRODUCT_PRICE
import ankit.com.sampleapp.util.Constants.SORT_BY_BEST_MATCH
import ankit.com.sampleapp.util.Constants.SORT_BY_DELIVERY_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_DISTANCE
import ankit.com.sampleapp.util.Constants.SORT_BY_MIN_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_NEWEST
import ankit.com.sampleapp.util.Constants.SORT_BY_POPULARITY
import ankit.com.sampleapp.util.Constants.SORT_BY_RATING_AVERAGE
import ankit.com.sampleapp.util.UIResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsDataSource: RestaurantsDataSource,
    private val restaurantsDao: RestaurantsDao
) : RestaurantsRepository {

    override suspend fun getRestaurants(): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        return flow {
            try {
                if (restaurantsDao.getRestaurants().isEmpty()) {
                    val restaurantsData = restaurantsDataSource.fetchRestaurantsInformation()
                    restaurantsDao.insertAll(restaurantsData)
                    emit(UIResponseState.Success(restaurantsData.toRestaurantsDomainList()))
                } else {
                    emit(UIResponseState.Success(restaurantsDao.getRestaurants().toRestaurantsDomainList()))
                }
            } catch (exception: Exception) {
                emit(UIResponseState.Error(exception))
            }
        }
    }

    override suspend fun addToFavorites(restaurantName: String, isFavorite: Boolean): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        restaurantsDao.updateRestaurantsAsFavorite(restaurantName, isFavorite)
        return flow {
            try {
                val restaurantData = restaurantsDao.getRestaurants()
                emit(UIResponseState.Success(restaurantData.toRestaurantsDomainList()))
            } catch (exception: Exception) {
                emit(UIResponseState.Error(exception))
            }
        }
    }

    override suspend fun getSortedRestaurantsList(sortBy: String): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        return flow {
            try {
                val restaurantList = restaurantsDao.getRestaurants()
                var sortedRestaurantList: List<Restaurant> = mutableListOf()
                when (sortBy){
                    SORT_BY_BEST_MATCH -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.bestMatch })
                    SORT_BY_NEWEST -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.newest })
                    SORT_BY_RATING_AVERAGE -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.ratingAverage }.reversed())
                    SORT_BY_DISTANCE -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.distance })
                    SORT_BY_POPULARITY -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.popularity })
                    SORT_BY_AVERAGE_PRODUCT_PRICE -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.averageProductPrice })
                    SORT_BY_DELIVERY_COST -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.deliveryCosts })
                    SORT_BY_MIN_COST -> sortedRestaurantList = restaurantList.sortedWith(compareBy<Restaurant>{it.favorite}.reversed().thenBy { it.status }.thenBy { it.sortingValues.minCost })
                }
                emit(UIResponseState.Success(sortedRestaurantList.toRestaurantsDomainList()))
            } catch (exception: Exception) {
                emit(UIResponseState.Error(exception))
            }
        }
    }
}
