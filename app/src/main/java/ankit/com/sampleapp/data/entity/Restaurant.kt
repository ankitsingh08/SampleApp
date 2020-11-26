package ankit.com.sampleapp.data.entity

import ankit.com.sampleapp.domain.model.RestaurantDomainModel

data class Restaurant(
    val sortingValues: SortingValues,
    val name: String = "",
    val status: String = ""
)

internal fun Restaurant.toDomainModel(): RestaurantDomainModel {
    return RestaurantDomainModel(
        sortingValues = this.sortingValues.toDomainModel(),
        name = this.name,
        status = this.status
    )
}
