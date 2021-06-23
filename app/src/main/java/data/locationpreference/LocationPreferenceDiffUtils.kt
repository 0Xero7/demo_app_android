package data.locationpreference

import androidx.recyclerview.widget.DiffUtil

class LocationPreferenceDiffUtils(
    private val oldList: List<LocationPreference>,
    private val newList: List<LocationPreference>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id && old.locationName == new.locationName
    }
}