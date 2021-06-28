package com.example.traveloptions

import adapters.TravelLocationAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveloptions.databinding.FragmentSecondBinding
import data.location.LocationRepository
import data.location.LocationViewModel
import data.location.LocationViewModelFactory
import decorations.GridItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LocationsFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var mLocationViewModel: LocationViewModel
    private lateinit var mRvAdapter : TravelLocationAdapter

    @Volatile
    private var isLoading : Boolean = false

    val args: LocationsFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val categoryID = args.categoryID
        var categoryName = args.categoryName

        binding.tvCategory.text = "$categoryName"
        binding.btBack.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()//navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        val factory = LocationViewModelFactory(LocationRepository(categoryID))
        mLocationViewModel = ViewModelProvider(this, factory).get(LocationViewModel::class.java)

        mRvAdapter = TravelLocationAdapter(categoryName)

        mLocationViewModel.locationList.observe(viewLifecycleOwner)  {
            mRvAdapter.differ.submitList(it.toList())
        }

        binding.rvLocations.adapter = mRvAdapter
        val mRvLayoutManager = GridLayoutManager(context, 2)
        binding.rvLocations.layoutManager = mRvLayoutManager

        val decor = DividerItemDecoration(context, RecyclerView.VERTICAL)
        decor.setDrawable( AppCompatResources.getDrawable(requireContext(), R.drawable.separator)!! )
        val spaceInPixels = 30
        binding.rvLocations.addItemDecoration(GridItemDecoration(spaceInPixels))

        binding.rvLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemScrolled = mRvLayoutManager.itemCount
                val lastVis = mRvLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = mRvAdapter.itemCount

                if (!isLoading && itemScrolled + lastVis >= total)
                    loadMore()
            }
        })

        loadMore()

        return binding.root
    }

    private fun loadMore() {
        mLocationViewModel.viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            mLocationViewModel.getLocations()
            isLoading = false
        }
    }
}