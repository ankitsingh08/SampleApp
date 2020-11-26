package ankit.com.sampleapp.data.repository

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.local.RestaurantsInformationAPI
import ankit.com.sampleapp.data.local.RestaurantsInformationAPIToEntityMapper
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsDataSource @Inject constructor(
    private val restaurantsInformationAPI: RestaurantsInformationAPI,
    private val restaurantsInformationAPIToEntityMapper: RestaurantsInformationAPIToEntityMapper) {

    suspend fun fetchRestaurantsInformation() : List<Restaurant> {
        return restaurantsInformationAPIToEntityMapper.transformTeamEntityCollection(
            restaurantsInformationAPI.fetchRestaurantsInformation()).restaurants
    }
}