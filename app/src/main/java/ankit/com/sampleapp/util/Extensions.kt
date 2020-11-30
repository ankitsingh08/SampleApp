package ankit.com.sampleapp.util

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.util.Constants.SORT_BY_AVERAGE_PRODUCT_PRICE
import ankit.com.sampleapp.util.Constants.SORT_BY_BEST_MATCH
import ankit.com.sampleapp.util.Constants.SORT_BY_DELIVERY_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_DISTANCE
import ankit.com.sampleapp.util.Constants.SORT_BY_MIN_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_NEWEST
import ankit.com.sampleapp.util.Constants.SORT_BY_POPULARITY
import ankit.com.sampleapp.util.Constants.SORT_BY_RATING_AVERAGE

/**
 * Created by AnkitSingh on 11/29/20.
 */
fun List<Restaurant>.sortByFavStatusAndSelectedSortTypeByAscending(sortBy: String): List<Restaurant> {
    return this.sortedWith(compareBy<Restaurant> {
        it.favorite
    }.reversed().thenBy { it.status }
            .thenBy {
                when (sortBy) {
                    SORT_BY_BEST_MATCH -> it.sortingValues.bestMatch
                    SORT_BY_NEWEST -> it.sortingValues.newest
                    SORT_BY_DISTANCE -> it.sortingValues.distance
                    SORT_BY_AVERAGE_PRODUCT_PRICE -> it.sortingValues.averageProductPrice
                    SORT_BY_DELIVERY_COST -> it.sortingValues.deliveryCosts
                    SORT_BY_MIN_COST -> it.sortingValues.minCost
                    else -> it.sortingValues.bestMatch
                }
            })
}

fun List<Restaurant>.sortByFavStatusAndSelectedSortTypeByDescending(sortBy: String): List<Restaurant> {
    return this.sortedWith(compareBy<Restaurant> {
        it.favorite
    }.reversed().thenBy { it.status }
            .thenByDescending {
                when (sortBy) {
                    SORT_BY_RATING_AVERAGE -> it.sortingValues.ratingAverage
                    SORT_BY_POPULARITY -> it.sortingValues.popularity
                    else -> it.sortingValues.bestMatch
                }
            })
}