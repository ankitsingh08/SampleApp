package ankit.com.sampleapp.view

import androidx.recyclerview.widget.DiffUtil
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.domain.model.RestaurantDomainModel

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsDiffCallback : DiffUtil.ItemCallback<RestaurantDomainModel>() {
    override fun areItemsTheSame(oldItem: RestaurantDomainModel, newItem: RestaurantDomainModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RestaurantDomainModel, newItem: RestaurantDomainModel): Boolean {
        return oldItem == newItem
    }

}