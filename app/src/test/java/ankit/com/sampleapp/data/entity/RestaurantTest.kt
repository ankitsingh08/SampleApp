package ankit.com.sampleapp.data.entity

import ankit.com.sampleapp.domain.DomainTestData
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by AnkitSingh on 11/29/20.
 */
@RunWith(JUnit4::class)
class RestaurantTest {

    @Test
    fun `data model Restaurant maps to RestaurantDomainModel`() {
        val sortingValues = SortingValues(1536.0, 96.0, 1190.0, 200.0, 17.0, 96.0, 1000.0, 4.5)
        val restaurantDataModel =  Restaurant(sortingValues, "De Amsterdamsche Tram", Status.OPEN, true)

        val restaurantDomainModel = restaurantDataModel.toDomainModel()

        assertEquals(restaurantDomainModel, RestaurantDomainModel(sortingValues.toDomainModel(), "De Amsterdamsche Tram", "open", true))
    }
}