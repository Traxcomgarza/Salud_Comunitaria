package com.example.data_core.firebase

import com.example.data_core.model.DiseaseInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDiseaseService(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

){
    //DISEASE FIREBASE
    private val collection = firestore.collection("disease")

    suspend fun uploadDisease(disease: DiseaseInfo){
        collection.document(disease.id.toString()).set(disease.toMap()).await()

    }
    suspend fun deleteDisease(disease: DiseaseInfo){
        collection.document(disease.id.toString()).delete().await()
    }

    suspend fun getAllDiseases(): List<DiseaseInfo>{
        val snapshot = collection.get().await()
        android.util.Log.d("DEBUG_DATA", "FirebaseService: Se encontraron ${snapshot.documents.size} documentos en Firebase.")
        return snapshot.documents.mapNotNull {
            it.data?.let { data -> DiseaseInfo.fromMap(data) }
        }

    }

}