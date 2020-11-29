package ankit.com.sampleapp.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.util.Constants.EMPTY_STRING

@Entity(tableName = "restaurants")
data class Restaurant(
    @Embedded
    val sortingValues: SortingValues,
    @PrimaryKey
    val name: String = EMPTY_STRING,
    val status: Status,
    val favorite: Boolean
)

fun Restaurant.toDomainModel(): RestaurantDomainModel {
    return RestaurantDomainModel(
        sortingValues = this.sortingValues.toDomainModel(),
        name = this.name,
        status = this.status.value,
        favorite = this.favorite
    )
}
