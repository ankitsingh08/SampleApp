package ankit.com.sampleapp.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import ankit.com.sampleapp.domain.usecase.GetRestaurantsUseCase
import ankit.com.sampleapp.util.successOr
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsViewModel @ViewModelInject constructor(
    private val restaurantsUseCase: GetRestaurantsUseCase
): ViewModel() {
    private val _restaurants = MutableLiveData<List<RestaurantDomainModel>>()
    val restaurants: LiveData<List<RestaurantDomainModel>> = _restaurants
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun getRestaurantsData() {
        viewModelScope.launch{
            restaurantsUseCase.execute()
                .map { it.successOr(emptyList()) }
                .collect {
                    _restaurants.value = it
                }
        }
    }
}