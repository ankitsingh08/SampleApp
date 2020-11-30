package ankit.com.sampleapp.data.repository

import ankit.com.sampleapp.data.database.RestaurantsDao
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.mapper.toRestaurantsDomainList
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.*
import ankit.com.sampleapp.util.Constants.SORT_BY_POPULARITY
import ankit.com.sampleapp.util.Constants.SORT_BY_RATING_AVERAGE
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
                //As we are using local data just reading from database if present instead of reading and parsing again and again,for actual service this logic will change
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
                val sortedRestaurantList: List<Restaurant>

                sortedRestaurantList = if (sortBy == SORT_BY_RATING_AVERAGE || sortBy == SORT_BY_POPULARITY) {
                    restaurantList.sortByFavStatusAndSelectedSortTypeByDescending(sortBy)
                } else {
                    restaurantList.sortByFavStatusAndSelectedSortTypeByAscending(sortBy)
                }

                emit(UIResponseState.Success(sortedRestaurantList.toRestaurantsDomainList()))
            } catch (exception: Exception) {
                emit(UIResponseState.Error(exception))
            }
        }
    }
}
