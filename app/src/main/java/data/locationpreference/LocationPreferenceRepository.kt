package data.locationpreference

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LocationPreferenceRepository {
    private val firestore = FirebaseFirestore.getInstance()

    private var getQuery = firestore.collection("categories")
        .orderBy("categoryName")
        .limit(5)

    suspend fun getLocations() : List<LocationPreference> {
        val result = getQuery.get().await()

        if (result.documents.isNotEmpty()) {
            val lastVisible = result.documents.last()
            getQuery = firestore.collection("categories")
                .orderBy("categoryName")
                .startAfter(lastVisible)
                .limit(5)

            val res = mutableListOf<LocationPreference>()
            for (change in result.documentChanges) {
                when (change.type) {
                    DocumentChange.Type.ADDED -> {
                        val model = LocationPreference(
                            change.document.id,
                            change.document["categoryName"] as String
                        )
                        res.add(model)
                    }

                }
            }
            return res
        }

        return listOf()
    }

    /*companion object {
        @Volatile
        private var instance: LocationPreferenceRepository? = null

        fun getInstance() : LocationPreferenceRepository = instance ?: synchronized(this) {
            instance ?: LocationPreferenceRepository().also { instance = it }
        }
    }*/
}