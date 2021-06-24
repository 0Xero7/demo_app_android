package data.locationpreference

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationPreferenceViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var locationPreferenceRepository: LocationPreferenceRepository

    @Volatile
    private var _isLoading: Boolean = false
    val isLoading = _isLoading

    fun create(repository: LocationPreferenceRepository) {
        locationPreferenceRepository = repository
    }

    private var _data: MutableList<LocationPreference> = mutableListOf()
    private var data: MutableLiveData<List<LocationPreference>> = MutableLiveData()
    var preferredLocations: LiveData<List<LocationPreference>> = data

    suspend fun getLocations() {
        val locations = locationPreferenceRepository.getLocations()
        for (i in locations)
            _data.add(i)
        data.postValue(_data)
    }
}