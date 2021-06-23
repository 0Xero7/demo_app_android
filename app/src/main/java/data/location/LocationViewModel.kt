package data.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import data.locationpreference.LocationPreference

class LocationViewModel() : ViewModel() {
    private lateinit var repository: LocationRepository

    fun create(repo: LocationRepository) {
        repository = repo
    }

    private val _data : MutableList<Location> = mutableListOf()
    private val data : MutableLiveData<List<Location>> = MutableLiveData()
    val locationList : LiveData<List<Location>> = data

    suspend fun getLocations() {
        val locations = repository.getLocations()
        _data.addAll(locations)
        data.postValue(_data)
    }
}