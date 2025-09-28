package com.example.data_core.firebase

import com.example.data_core.model.Suggestion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseSuggestionService(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    // SUGGESTION FIREBASE
    private val collection = firestore.collection("suggestion")

    suspend fun uploadSuggestion(suggestion: Suggestion) {
        collection.document(suggestion.id.toString()).set(suggestion.toMap()).await()
    }

    suspend fun deleteSuggestion(suggestion: Suggestion) {
        collection.document(suggestion.id.toString()).delete().await()
    }

    suspend fun getAllSuggestions(): List<Suggestion> {
        val snapshot = collection.get().await()
        android.util.Log.d(
            "DEBUG_DATA",
            "FirebaseService: Se encontraron ${snapshot.documents.size} sugerencias en Firebase."
        )
        return snapshot.documents.mapNotNull {
            it.data?.let { data -> Suggestion.fromMap(data) }
        }
    }
}