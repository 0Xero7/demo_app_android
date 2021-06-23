package data.location

import androidx.recyclerview.widget.DiffUtil

class LocationDiffUtils(
    private val oldList: List<Location>,
    private val newList: List<Location>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return old.id == new.id && old.categoryId == new.categoryId && old.likes == new.likes && old.locationName == new.locationName
                && old.description == new.description
    }
}