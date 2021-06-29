package com.example.traveloptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.traveloptions.databinding.FragmentLocationDetailBinding
import com.example.traveloptions.databinding.FragmentLocationImageBinding

class LocationImageFragment : Fragment() {

    private var _binding: FragmentLocationImageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args : LocationImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationImageBinding.inflate(inflater, container, false)

        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val location = args.location

        Glide.with(requireContext())
            .load(location.url)
            .into(binding.zvLocationImage)


        return binding.root
    }
}