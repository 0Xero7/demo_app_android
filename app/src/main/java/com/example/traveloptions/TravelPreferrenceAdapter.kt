package com.example.traveloptions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import data.locationpreference.LocationPreference
import data.locationpreference.LocationPreferenceDiffUtils

class TravelPreferrenceAdapter : RecyclerView.Adapter<TravelPreferrenceAdapter.TravelPreferenceViewHolder>() {
    class TravelPreferenceViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private var locationPreferenceList: List<LocationPreference> = emptyList()

    fun setData(models: List<LocationPreference>) {
        val diffUtils = LocationPreferenceDiffUtils(locationPreferenceList, models)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        locationPreferenceList = models.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelPreferenceViewHolder {
        return TravelPreferenceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_preference_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TravelPreferenceViewHolder, position: Int) {
        val item = locationPreferenceList[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.tvLocationName).text = item.locationName

            setOnClickListener {
                val action = LocationPreferenceFragmentDirections.showLocations(item.id, item.locationName)
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return locationPreferenceList.size
    }
}