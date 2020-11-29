package ankit.com.sampleapp.data.mapper

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.entity.toDomainModel
import ankit.com.sampleapp.domain.model.RestaurantDomainModel

/**
 * Created by AnkitSingh on 11/26/20.
 */
inline fun List<Restaurant>.toRestaurantsDomainList(): List<RestaurantDomainModel> {
    return this.map {
        it.toDomainModel()
    }
}