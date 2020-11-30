package ankit.com.sampleapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ankit.com.sampleapp.R
import ankit.com.sampleapp.data.entity.Restaurant
import ankit.com.sampleapp.databinding.RestaurantItemBinding
import ankit.com.sampleapp.domain.model.RestaurantDomainModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsAdapter (val listener: OnClickHandler):
    ListAdapter<RestaurantDomainModel, RecyclerView.ViewHolder>(RestaurantsDiffCallback()),
    Filterable {

    private lateinit var restaurantsList: List<RestaurantDomainModel>
    private lateinit var sortBy: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantsViewHolder(
            RestaurantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val restaurant = getItem(position)
        (holder as RestaurantsViewHolder).bind(listener, restaurant, sortBy)
    }

    fun setRestaurantsList(list: List<RestaurantDomainModel>, sortBy: String) {
        this.restaurantsList = list
        this.sortBy = sortBy
        notifyDataSetChanged()
    }

    class RestaurantsViewHolder(private val binding: RestaurantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: OnClickHandler, item: RestaurantDomainModel, sortBy: String) {
            binding.apply {
                restaurant = item
                this.sortBy = sortBy
                executePendingBindings()
            }

            if (item.favorite) {
                binding.favoriteView.setImageResource(R.drawable.ic_favorite_red_24dp)
            } else {
                binding.favoriteView.setImageResource(R.drawable.ic_favorite_shadow_24dp)
            }

            binding.favoriteView.setOnClickListener {
                if (item.favorite) {
                    binding.favoriteView.setImageResource(R.drawable.ic_favorite_shadow_24dp)
                    listener.onItemClick(item.name, false)
                } else {
                    binding.favoriteView.setImageResource(R.drawable.ic_favorite_red_24dp)
                    listener.onItemClick(item.name, true)
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var filteredList = ArrayList<RestaurantDomainModel>()
                if (charSearch.isEmpty()) {
                    filteredList = restaurantsList as ArrayList<RestaurantDomainModel>
                } else {
                    for (restaurant in restaurantsList) {
                        if (restaurant.name.toLowerCase(Locale.ROOT).contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            filteredList.add(restaurant)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as ArrayList<RestaurantDomainModel>)
            }

        }
    }

    interface OnClickHandler{
        fun onItemClick(restaurantName: String, isFavorite: Boolean)
    }
}