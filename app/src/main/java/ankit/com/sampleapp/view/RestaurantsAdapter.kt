package ankit.com.sampleapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.databinding.RestaurantItemBinding
import ankit.com.sampleapp.domain.model.RestaurantDomainModel

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsAdapter: ListAdapter<RestaurantDomainModel, RecyclerView.ViewHolder>(RestaurantsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantsViewHolder(RestaurantItemBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val restaurant = getItem(position)
        (holder as RestaurantsViewHolder).bind(restaurant)
    }

    class RestaurantsViewHolder(private val binding: RestaurantItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RestaurantDomainModel) {
            binding.apply {
                restaurant = item
                executePendingBindings()
            }
        }
    }
}