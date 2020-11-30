package ankit.com.sampleapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ankit.com.sampleapp.R
import ankit.com.sampleapp.databinding.RestaurantsFragmentBinding
import ankit.com.sampleapp.util.Constants.SORT_BY_AVERAGE_PRODUCT_PRICE
import ankit.com.sampleapp.util.Constants.SORT_BY_BEST_MATCH
import ankit.com.sampleapp.util.Constants.SORT_BY_DELIVERY_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_DISTANCE
import ankit.com.sampleapp.util.Constants.SORT_BY_MIN_COST
import ankit.com.sampleapp.util.Constants.SORT_BY_NEWEST
import ankit.com.sampleapp.util.Constants.SORT_BY_POPULARITY
import ankit.com.sampleapp.util.Constants.SORT_BY_RATING_AVERAGE
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by AnkitSingh on 11/25/20.
 */
@AndroidEntryPoint
class RestaurantsFragment : Fragment(), PopupMenu.OnMenuItemClickListener,
    RestaurantsAdapter.OnClickHandler {

    private lateinit var binding: RestaurantsFragmentBinding

    private var sortBy = SORT_BY_BEST_MATCH

    private val restaurantsViewModel: RestaurantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RestaurantsFragmentBinding.inflate(inflater, container, false)
        initializeUI()
        return binding.root
    }

    private fun initializeUI() {
        val adapter = RestaurantsAdapter(this)
        binding.rvRestaurants.adapter = adapter

        //get Restaurants data
        restaurantsViewModel.getRestaurantsData()

        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, Observer {
            adapter.setRestaurantsList(it, sortBy)
            adapter.submitList(it)
        })

        //set up search
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sort) {
            showPopup()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopup() {
        activity?.let {
            val popup = PopupMenu(context!!, activity?.findViewById(R.id.action_sort)!!)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.actions, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        sortBy = when (item?.itemId) {
            R.id.action_best_match -> SORT_BY_BEST_MATCH
            R.id.action_sort_newest -> SORT_BY_NEWEST
            R.id.action_sort_rating_average -> SORT_BY_RATING_AVERAGE
            R.id.action_sort_distance -> SORT_BY_DISTANCE
            R.id.action_sort_popularity -> SORT_BY_POPULARITY
            R.id.action_sort_product_price -> SORT_BY_AVERAGE_PRODUCT_PRICE
            R.id.action_sort_delivery_cost -> SORT_BY_DELIVERY_COST
            R.id.action_sort_min_cost -> SORT_BY_MIN_COST
            else -> SORT_BY_BEST_MATCH
        }
        restaurantsViewModel.getRestaurantsDataBySelectionType(sortBy)
        return false
    }

    override fun onItemClick(restaurantName: String, isFavorite: Boolean) {
        restaurantsViewModel.addToFavorites(restaurantName, isFavorite)
    }
}