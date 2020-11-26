package ankit.com.sampleapp.view

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ankit.com.sampleapp.R
import ankit.com.sampleapp.databinding.RestaurantsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by AnkitSingh on 11/25/20.
 */
@AndroidEntryPoint
class RestaurantsFragment: Fragment() {

    private lateinit var binding: RestaurantsFragmentBinding

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
        setUpFilterSpinner()

        val adapter = RestaurantsAdapter()
        binding.rvRestaurants.adapter = adapter

        //get Restaurants data
        restaurantsViewModel.getRestaurantsData()

        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setUpFilterSpinner() {
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.filter_options_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.filterSpinner.adapter = adapter
            }
        }
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
}