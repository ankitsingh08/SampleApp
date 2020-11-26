package ankit.com.sampleapp.domain

import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.model.SortingValuesDomainModel

/**
 * Created by AnkitSingh on 11/25/20.
 */
object DomainTestData {

internal fun getRestaurantsDummyData(): List<RestaurantDomainModel> {
        val sortingValues1 = SortingValuesDomainModel(1536, 96, 1190, 200, 17, 96, 1000, 4.5)
        val restaurant1 = RestaurantDomainModel(sortingValues1, "Tanoshii Sushi", "order ahead")
        val sortingValues2 = SortingValuesDomainModel(1536, 96, 1190, 200, 17, 96, 1000, 4.5)
        val restaurant2 = RestaurantDomainModel(sortingValues2, "Royal Thai", "open")
        val restaurantList = mutableListOf<RestaurantDomainModel>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        return restaurantList
    }
}