package ankit.com.sampleapp.data.entity

import androidx.room.Entity
import ankit.com.sampleapp.domain.model.SortingValuesDomainModel

data class SortingValues(
    val averageProductPrice: Int = 0,
    val bestMatch: Int = 0,
    val distance: Int = 0,
    val deliveryCosts: Int = 0,
    val popularity: Int = 0,
    val newest: Int = 0,
    val minCost: Int = 0,
    val ratingAverage: Double = 0.0
)

internal fun SortingValues.toDomainModel() = SortingValuesDomainModel(
    averageProductPrice = this.averageProductPrice,
    bestMatch = this.bestMatch,
    distance = this.distance,
    deliveryCosts = this.deliveryCosts,
    popularity = this.popularity,
    newest = this.newest,
    minCost = this.minCost,
    ratingAverage = this.ratingAverage
)