package ankit.com.sampleapp.domain.usecase

import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import ankit.com.sampleapp.util.UIResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/28/20.
 */
class FilterRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {
    suspend fun execute(sortBy: String): Flow<UIResponseState<List<RestaurantDomainModel>>> {
        return repository.getSortedRestaurantsList(sortBy)
    }
}