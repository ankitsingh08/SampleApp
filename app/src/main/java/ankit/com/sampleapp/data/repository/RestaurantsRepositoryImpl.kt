package ankit.com.sampleapp.data.repository

import ankit.com.sampleapp.data.mapper.toRestaurantsList
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.UIResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsDataSource: RestaurantsDataSource
) : RestaurantsRepository {

    override suspend fun getRestaurants(): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        return flow {
            try {
                val restaurantsData = restaurantsDataSource.fetchRestaurantsInformation()
                emit(UIResponseState.Success(restaurantsData.toRestaurantsList()))
            } catch (exception: Exception) {
                emit(UIResponseState.Error(exception))
            }
        }
    }
}
