package ankit.com.sampleapp.data.entity

import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.model.SortingValuesDomainModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by AnkitSingh on 11/29/20.
 */
@RunWith(JUnit4::class)
class SortingValuesTest {
    @Test
    fun `data model SortingValues maps to SortingValuesDomainModel`() {
        val sortingValues = SortingValues(1536, 96, 1190, 200, 17, 96, 1000, 4.5)

        val sortingValuesDomainModel = sortingValues.toDomainModel()

        assertEquals(sortingValuesDomainModel, SortingValuesDomainModel(1536, 96, 1190, 200, 17, 96, 1000, 4.5))
    }
}