package com.example.traveloptions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import data.location.Location
import data.location.LocationDiffUtils

class TravelLocationAdapter(private val category: String) : RecyclerView.Adapter<TravelLocationAdapter.TravelLoationViewHolder>() {
    class TravelLoationViewHolder(item : View) : RecyclerView.ViewHolder(item)

    private var locations : List<Location> = emptyList()

    fun setData(locs: List<Location>) {
        val diffUtils = LocationDiffUtils(locations, locs)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        locations = locs.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelLoationViewHolder {
        return TravelLoationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TravelLoationViewHolder, position: Int) {
        val item = locations[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.tvLocationName).text = item.locationName
            findViewById<TextView>(R.id.tvLocationLikes).text = "${item.likes} likes"

            setOnClickListener {
                val action = SecondFragmentDirections.showLocationDetail(item, category)
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = locations.size
}