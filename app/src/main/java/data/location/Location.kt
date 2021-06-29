package data.location

import android.os.Parcel
import android.os.Parcelable
import data.dummydata.DummyLocationImages
import kotlin.random.Random

class Location(
    val id : String,
    val categoryId : String,
    val locationName : String,
    val description : String,
    val likes : Int,
    val url : String = DummyLocationImages.locationUrls.random(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
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
        parcel.writeString(url)
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