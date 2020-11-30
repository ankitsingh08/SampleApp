package ankit.com.sampleapp.domain.model

import ankit.com.sampleapp.util.Constants.SORT_BY_AVERAGE_PRODUCT_PRICE
import ankit.com.sampleapp.util.Constants.SORT_BY_BEST_MATCH
import ankit.com.sampleapp.util.Constants.SORT_BY_DELIVERY_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_DISTANCE
import ankit.com.sampleapp.util.Constants.SORT_BY_MIN_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_NEWEST
import ankit.com.sampleapp.util.Constants.SORT_BY_POPULARITY
import ankit.com.sampleapp.util.Constants.SORT_BY_RATING_AVERAGE

data class SortingValuesDomainModel(val averageProductPrice: Double = 0.0,
                                    val bestMatch: Double = 0.0,
                                    val distance: Double = 0.0,
                                    val deliveryCosts: Double = 0.0,
                                    val popularity: Double = 0.0,
                                    val newest: Double = 0.0,
                                    val minCost: Double = 0.0,
                                    val ratingAverage: Double = 0.0) {

    //Return value as string to show on ui for selected filter
    fun getValueForKey(parameterName: String): String {
        return when(parameterName) {
            SORT_BY_BEST_MATCH -> this.bestMatch.toString()
            SORT_BY_AVERAGE_PRODUCT_PRICE -> this.averageProductPrice.toString()
            SORT_BY_DISTANCE -> this.distance.toString()
            SORT_BY_DELIVERY_COST -> this.deliveryCosts.toString()
            SORT_BY_POPULARITY -> this.popularity.toString()
            SORT_BY_NEWEST -> this.newest.toString()
            SORT_BY_MIN_COST -> this.minCost.toString()
            SORT_BY_RATING_AVERAGE -> this.ratingAverage.toString()
            else -> this.bestMatch.toString()
        }
    }
}