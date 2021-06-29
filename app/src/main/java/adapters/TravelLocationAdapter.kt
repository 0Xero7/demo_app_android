package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.traveloptions.R
import com.example.traveloptions.LocationsFragmentDirections
import data.location.Location

class TravelLocationAdapter(private val category: String) : RecyclerView.Adapter<TravelLocationAdapter.TravelLoationViewHolder>() {
    inner class TravelLoationViewHolder(item : View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(old: Location, new: Location): Boolean {
            return old.id == new.id && old.categoryId == new.categoryId && old.likes == new.likes
                    && old.locationName == new.locationName && old.description == new.description
                    && old.url == new.url
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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
        val item = differ.currentList[position]

        val iv = holder.itemView.findViewById<ImageView>(R.id.ivLocationImageThumb)

        val options = RequestOptions().transform(CenterCrop(), RoundedCorners(10))
        Glide.with(holder.itemView)
            .load(item.url)
            .apply(options)
            .into(iv)

        holder.itemView.apply {
            findViewById<TextView>(R.id.tvLocationName).text = item.locationName
            findViewById<TextView>(R.id.tvLocationLikes).text = "${item.likes} likes"

            setOnClickListener {
                val action = LocationsFragmentDirections.showLocationDetail(item, category)
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}