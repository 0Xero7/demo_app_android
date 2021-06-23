package com.example.traveloptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.traveloptions.databinding.FragmentLocationDetailBinding
import com.example.traveloptions.databinding.FragmentSecondBinding

class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: LocationDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)

        val location = args.location
        val category = args.categoryName

        binding.tvLocationDetailName.text = location.locationName
        binding.tvLocationDetailCategory.text = category
        binding.tvLocationDetailDescription.text = location.description

        binding.imageButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }
}