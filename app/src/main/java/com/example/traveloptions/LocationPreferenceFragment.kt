package com.example.traveloptions

import adapters.TravelPreferrenceAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveloptions.databinding.FragmentFirstBinding
import data.locationpreference.LocationPreferenceRepository
import data.locationpreference.LocationPreferenceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationPreferenceFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var locPreferrenceAdapter: TravelPreferrenceAdapter
    private val mLocationPreferenceViewModel : LocationPreferenceViewModel by activityViewModels()

    @Volatile
    private var isLoading : Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        locPreferrenceAdapter = TravelPreferrenceAdapter()

        if (mLocationPreferenceViewModel.preferredLocations.value.isNullOrEmpty())
            mLocationPreferenceViewModel.create(LocationPreferenceRepository())

        mLocationPreferenceViewModel.preferredLocations.observe(viewLifecycleOwner) {
            locPreferrenceAdapter.differ.submitList(it.toList())
        }

        binding.rvLocationPref.adapter = locPreferrenceAdapter
        val layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        binding.rvLocationPref.layoutManager = layoutManager

        val decor = PreferenceItemDecoration(20)
        binding.rvLocationPref.addItemDecoration(decor)

        binding.rvLocationPref.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.itemCount
                val pastItemCount = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = locPreferrenceAdapter.itemCount

                if (!isLoading && visibleItemCount + pastItemCount >= total) {
                    loadMore()
                }

            }
        })

        loadMore()
        return binding.root
    }

    private fun loadMore() {
        mLocationPreferenceViewModel.viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            mLocationPreferenceViewModel.getLocations()
            isLoading = false
        }
    }
}