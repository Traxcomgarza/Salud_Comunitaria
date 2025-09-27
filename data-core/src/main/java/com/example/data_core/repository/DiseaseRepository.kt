package com.example.data_core.repository

import android.util.Log
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.dao.UserDao
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.UserDiseaseCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DiseaseRepository(
    private val diseaseDao: DiseaseDao,
    private val userDao: UserDao,
    private val firebaseService: FirebaseDiseaseService
) {
    fun getAllDiseases(): Flow<List<DiseaseInfo>> = diseaseDao.getAllDiseases()

    fun getDiseaseById(id: Long): Flow<DiseaseInfo?> = diseaseDao.getDiseaseById(id)

    suspend fun insertDisease(disease: DiseaseInfo) {
        //insert in room to get the id
        val generatedId = diseaseDao.insertDisease(disease)
        val diseaseWithId = disease.copy(id = generatedId)
        try {
            firebaseService.uploadDisease(diseaseWithId)
            // _ ignoress the exception
        } catch (_: Exception) {
        }
    }

    suspend fun updateDisease(disease: DiseaseInfo) {
        diseaseDao.updateDisease(disease)
        try {
            firebaseService.uploadDisease(disease)
            // _ ignoress the exception
        } catch (_: Exception) {
        }
    }

    suspend fun deleteDisease(disease: DiseaseInfo) {
        diseaseDao.deleteDisease(disease)
        try {
            firebaseService.deleteDisease(disease)
            // _ ignoress the exception
        } catch (_: Exception) {
        }
    }

    suspend fun refreshDiseases() {
        try {
            val remoteDiseases = firebaseService.getAllDiseases()
            diseaseDao.insertAll(remoteDiseases)
        } catch (e: Exception) {
            Log.e("DiseaseRepository", "Fallo al sincronizar las enfermedades desde el servidor", e)
        }
    }

    suspend fun syncFromFirebase() {
        try {
            val remoteDisease = firebaseService.getAllDiseases()
            val localDisease = diseaseDao.getAllDiseases().first()

            remoteDisease.forEach { remote ->
                if (localDisease.none { it.id == remote.id }) {
                    diseaseDao.insertDisease(remote)
                }
            }
        } catch (_: Exception) {
        }
    }

    suspend fun addDiseaseToUserHistory(userId: Long, diseaseId: Long): Boolean { // Devuelve Boolean
        return try { // Retorna el resultado del bloque try-catch
            val relation = UserDiseaseCrossRef(userId = userId, diseaseInfoId = diseaseId)
            userDao.addUserDiseaseLink(relation)
            Log.d(
                "DiseaseRepository",
                "Enfermedad $diseaseId agregada al historial del usuario $userId"
            )
            true // Éxito
        } catch (e: Exception) {
            Log.e("DiseaseRepository", "Error al agregar enfermedad al historial: ${e.message}", e) // Loguea el mensaje del error
            false // Fallo
        }
    }
}