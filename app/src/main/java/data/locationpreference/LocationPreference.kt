package data.locationpreference

data class LocationPreference(
    val id: String,
    val locationName: String
) {
    override fun toString(): String {
        return "$id : $locationName"
    }
}