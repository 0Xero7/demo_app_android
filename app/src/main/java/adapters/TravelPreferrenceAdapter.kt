package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.traveloptions.LocationPreferenceFragmentDirections
import com.example.traveloptions.R
import data.locationpreference.LocationPreference

class TravelPreferrenceAdapter : RecyclerView.Adapter<TravelPreferrenceAdapter.TravelPreferenceViewHolder>() {
    inner class TravelPreferenceViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val options = RequestOptions().transform(
        CenterCrop(),
        RoundedCorners(8)
    ).override(40, 40)

    private val differCallback = object : DiffUtil.ItemCallback<LocationPreference>() {
        override fun areItemsTheSame(oldItem: LocationPreference, newItem: LocationPreference): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(old: LocationPreference, new: LocationPreference): Boolean {
            return old.id == new.id && old.locationName == new.locationName && old.url == new.url
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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
        val item = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(item.url)
            .apply(options)
            .into(holder.itemView.findViewById(R.id.imageView))

        holder.itemView.apply {
            findViewById<TextView>(R.id.tvLocationName).text = item.locationName

            setOnClickListener {
                val action =
                    LocationPreferenceFragmentDirections.showLocations(item.id, item.locationName)
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}