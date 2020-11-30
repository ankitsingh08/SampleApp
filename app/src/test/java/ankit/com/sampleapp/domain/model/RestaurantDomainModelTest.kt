package ankit.com.sampleapp.domain.model

import ankit.com.sampleapp.domain.DomainTestData
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by AnkitSingh on 11/29/20.
 */
@RunWith(JUnit4::class)
class RestaurantDomainModelTest {
    private lateinit var restaurantDomainModel: RestaurantDomainModel

    @Test
    fun `model has default favorite as false`() {
        val testSortingValuesDomainModel = SortingValuesDomainModel(0.0, 96.0, 45.0, 272.0, 873.0,282.0,2828.0,4.5 )
        restaurantDomainModel = RestaurantDomainModel(testSortingValuesDomainModel, "Royal Thai", "open")

        assertFalse(restaurantDomainModel.favorite)
    }
}