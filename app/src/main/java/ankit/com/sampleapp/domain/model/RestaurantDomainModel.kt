package ankit.com.sampleapp.domain.model

data class RestaurantDomainModel(val sortingValues: SortingValuesDomainModel,
                                 val name: String = "",
                                 val status: String = "")