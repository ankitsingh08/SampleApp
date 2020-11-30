package ankit.com.sampleapp.domain

import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.data.entity.SortingValues
import ankit.com.sampleapp.data.entity.Status
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.model.SortingValuesDomainModel

/**
 * Created by AnkitSingh on 11/25/20.
 */
object DomainTestData {

internal fun getRestaurantsDomainData(): List<RestaurantDomainModel> {
        val sortingValues1 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant1 = RestaurantDomainModel(sortingValues1, "Tanoshii Sushi", "order ahead")
        val sortingValues2 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = RestaurantDomainModel(sortingValues2, "Royal Thai", "open")
        val restaurantList = mutableListOf<RestaurantDomainModel>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        return restaurantList
    }

internal fun getRestaurantsFilteredDummyData(): List<RestaurantDomainModel> {
        val sortingValues1 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant1 = RestaurantDomainModel(sortingValues1, "Tanoshii Sushi", "order ahead")
        val sortingValues2 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = RestaurantDomainModel(sortingValues2, "Royal Thai", "open")
        val restaurantList = mutableListOf<RestaurantDomainModel>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        return restaurantList
        }

internal fun getRestaurantsWithFavoritesDummyData(): List<RestaurantDomainModel> {
        val sortingValues1 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant1 = RestaurantDomainModel(sortingValues1, "De Amsterdamsche Tram", "order ahead", true)
        val sortingValues2 = SortingValuesDomainModel(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = RestaurantDomainModel(sortingValues2, "Royal Thai", "open", false)
        val restaurantList = mutableListOf<RestaurantDomainModel>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        return restaurantList
        }

internal fun getRestaurantData(): List<Restaurant> {
        val sortingValues1 = SortingValues(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant1 = Restaurant(sortingValues1, "Tanoshii Sushi", Status.OPEN, false)
        val sortingValues2 = SortingValues(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurant2 = Restaurant(sortingValues2, "Royal Thai", Status.OPEN, false)
        val restaurantList = mutableListOf<Restaurant>()
        restaurantList.add(restaurant1)
        restaurantList.add(restaurant2)
        return restaurantList
}
}