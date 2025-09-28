package com.example.data_core.firebase

import com.example.data_core.model.HistoryEntryFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseMedicalHistoryService(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private fun entriesRef(uid: String) =
        db.collection("user_histories")
            .document(uid)
            .collection("entries")

    suspend fun addDiseaseToHistory(uid: String, diseaseId: Long, name: String): Boolean {
        return try {
            val docRef = entriesRef(uid).document(diseaseId.toString())
            val data = mapOf(
                "diseaseId" to diseaseId,
                "name" to name,
                "createdAt" to com.google.firebase.Timestamp.now()
            )
            docRef.set(data, SetOptions.merge()).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    fun observeHistory(uid: String): Flow<List<HistoryEntryFirebase>> = callbackFlow {
        val reg = entriesRef(uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                val list = snap?.documents?.mapNotNull { doc ->
                    val diseaseId = (doc.getLong("diseaseId") ?: 0L)
                    val name = doc.getString("name") ?: ""
                    val createdAt = doc.getTimestamp("createdAt")?.toDate()?.time
                    HistoryEntryFirebase(diseaseId = diseaseId, name = name, createdAt = createdAt)
                } ?: emptyList()
                trySend(list).isSuccess
            }
        awaitClose { reg.remove() }
    }
}