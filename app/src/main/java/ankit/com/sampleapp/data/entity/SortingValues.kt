package ankit.com.sampleapp.data.entity

import ankit.com.sampleapp.domain.model.SortingValuesDomainModel

data class SortingValues(
    val averageProductPrice: Double = 0.0,
    val bestMatch: Double = 0.0,
    val distance: Double = 0.0,
    val deliveryCosts: Double = 0.0,
    val popularity: Double = 0.0,
    val newest: Double = 0.0,
    val minCost: Double = 0.0,
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