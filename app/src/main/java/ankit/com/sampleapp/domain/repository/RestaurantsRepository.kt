package ankit.com.sampleapp.domain.repository

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.util.UIResponseState
import kotlinx.coroutines.flow.Flow

/**
 * Created by AnkitSingh on 11/25/20.
 */
interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<UIResponseState<List<RestaurantDomainModel>>>
}