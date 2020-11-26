package ankit.com.sampleapp.domain.usecase

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.UIResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
class GetRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {
    suspend fun execute(): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        return repository.getRestaurants()
    }
}