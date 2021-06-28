package com.example.traveloptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

        val crop = RequestOptions().transform(CenterCrop(), RoundedCorners(16))

        Glide.with(requireContext())
            .load("https://cdn.stocksnap.io/img-thumbs/960w/empty-road_Q1W4K2AN0F.jpg")
            .apply(crop)
            .into(binding.ivLocationImage)

        binding.tvLocationDetailName.text = location.locationName
        binding.tvLocationDetailCategory.text = category
        binding.tvLocationDetailDescription.text = location.description



        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}