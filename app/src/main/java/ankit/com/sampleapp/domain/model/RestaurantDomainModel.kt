package ankit.com.sampleapp.domain.model

import ankit.com.sampleapp.util.Constants.EMPTY_STRING

data class RestaurantDomainModel(val sortingValues: SortingValuesDomainModel,
                                 val name: String = EMPTY_STRING,
                                 val status: String = EMPTY_STRING,
                                 val favorite: Boolean = false
)