package ankit.com.sampleapp.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.usecase.AddToFavoritesUseCase
import ankit.com.sampleapp.domain.usecase.FilterRestaurantsUseCase
import ankit.com.sampleapp.domain.usecase.GetRestaurantsUseCase
import ankit.com.sampleapp.util.Constants.SORT_BY_BEST_MATCH
import ankit.com.sampleapp.util.successOr
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsViewModel @ViewModelInject constructor(
    private val restaurantsUseCase: GetRestaurantsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val filterRestaurantsUseCase: FilterRestaurantsUseCase
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<RestaurantDomainModel>>()
    val restaurants: LiveData<List<RestaurantDomainModel>> = _restaurants
    private val _sortByCategoryName = MutableLiveData<String>()
    val sortByCategoryName: LiveData<String> = _sortByCategoryName
    private val _sortByCategoryValue = MutableLiveData<Any>()
    val sortByCategoryValue: LiveData<Any> = _sortByCategoryValue

    fun getRestaurantsData() {
        viewModelScope.launch {
            restaurantsUseCase.execute()
                .map { it.successOr(emptyList()) }
                .collect {
                    _restaurants.value = it
                }
        }
    }

    fun addToFavorites(restaurantName: String, isFavorite: Boolean) {
        viewModelScope.launch {
            addToFavoritesUseCase.execute(restaurantName, isFavorite)
                .map { it.successOr(emptyList()) }
                .collect {
                    _restaurants.value = it
                }
        }
    }

    fun getRestaurantsDataBySelectionType(sortBy: String) {
        viewModelScope.launch {
            filterRestaurantsUseCase.execute(sortBy)
                .map { it.successOr(emptyList()) }
                .collect { _restaurants.value = it }
        }
    }
}