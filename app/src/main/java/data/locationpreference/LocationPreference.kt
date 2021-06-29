package data.locationpreference

import data.dummydata.DummyLocationImages
import kotlin.random.Random

data class LocationPreference(
    val id: String,
    val locationName: String,
    val url : String = DummyLocationImages.locationUrls.random()
) {
    override fun toString(): String {
        return "$id : $locationName"
    }
}