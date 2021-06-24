package data.locationpreference

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.security.InvalidParameterException

class LocationPreferenceViewModelFactory(private val repository: LocationPreferenceRepository,
                                         private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationPreferenceViewModel::class.java))
            return LocationPreferenceViewModel(repository, application) as T

        throw InvalidParameterException()
    }
}