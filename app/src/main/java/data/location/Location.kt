package data.location

import android.os.Parcel
import android.os.Parcelable

class Location(
    val id : String,
    val categoryId : String,
    val locationName : String,
    val description : String,
    val likes : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "($categoryId) $locationName with $likes likes"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(categoryId)
        parcel.writeString(locationName)
        parcel.writeString(description)
        parcel.writeInt(likes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}