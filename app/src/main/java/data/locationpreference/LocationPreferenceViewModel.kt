package data.locationpreference

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot

class LocationPreferenceViewModel(application: Application)
    : AndroidViewModel(application) {

    private lateinit var locationPreferenceRepository: LocationPreferenceRepository

    fun create(repository: LocationPreferenceRepository) {
        locationPreferenceRepository = repository
    }

    private var _data : MutableList<LocationPreference> = mutableListOf()
    private var data : MutableLiveData<List<LocationPreference>> = MutableLiveData()
    var preferredLocations : LiveData<List<LocationPreference>> = data

    suspend fun getLocations()  {
        val locations = locationPreferenceRepository.getLocations()

        for (i in locations)
            _data.add(i)

        data.postValue(_data)
    }

}