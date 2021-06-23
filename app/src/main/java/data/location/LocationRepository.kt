package data.location

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class LocationRepository(private val categoryId : String) {
    private val instance = FirebaseFirestore.getInstance()

    private var getQuery : Query = instance.collection("categories/$categoryId/location")
        .orderBy("likes", Query.Direction.DESCENDING)
        .limit(10)

    suspend fun getLocations() : List<Location> {
        val result = getQuery.get().await()

        if (result.documents.isNotEmpty()) {
            getQuery = instance.collection("categories/$categoryId/location")
                .orderBy("likes", Query.Direction.DESCENDING)
                .startAfter(result.documents.last())
                .limit(10)

            val res = mutableListOf<Location>()
            for (change in result.documentChanges) {
                when (change.type) {
                    DocumentChange.Type.ADDED -> {
                        val model = Location(
                            change.document.id,
                            change.document["categoryID"] as String,
                            change.document["locationName"] as String,
                            change.document["description"] as String,
                            (change.document["likes"] as Long).toInt()
                        )
                        res.add(model)
                    }

                }
            }
            return res
        }

        return emptyList()
    }


    companion object {
        @Volatile
        private var instance : LocationRepository? = null

        fun getInstance(categoryId : String) = instance ?: synchronized(this) {
            instance ?: LocationRepository(categoryId).also { instance = it }
        }
    }
}