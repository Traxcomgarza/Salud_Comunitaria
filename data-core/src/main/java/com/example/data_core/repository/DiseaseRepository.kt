package com.example.data_core.repository

import android.util.Log
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.model.DiseaseInfo
import kotlinx.coroutines.flow.Flow

class DiseaseRepository(
    private val diseaseDao: DiseaseDao,
    private val firebaseService: FirebaseDiseaseService
){
    suspend fun refreshDiseases() {
        try {
            val remoteDiseases = firebaseService.getAllDiseases()
            android.util.Log.d("DEBUG_DATA", "Repository: Se recibieron ${remoteDiseases.size} enfermedades desde el servicio.")
            diseaseDao.insertAll(remoteDiseases)
        } catch (e: Exception) {
            Log.e("DiseaseRepository", "Fallo al sincronizar las enfermedades desde el servidor", e)
        }
    }
    fun getAllDiseases(): Flow<List<DiseaseInfo>> = diseaseDao.getAllDiseases()

    fun getDiseaseById(id: Long): Flow<DiseaseInfo?> = diseaseDao.getDiseaseById(id)
    suspend fun insertDisease(disease: DiseaseInfo){
        diseaseDao.insertDisease(disease)
        try {
            firebaseService.uploadDisease(disease)
            // _ ignoress the exception
        } catch (_: Exception){
        }
    }
    suspend fun updateDisease(disease: DiseaseInfo) {
        diseaseDao.updateDisease(disease)
        try {
            firebaseService.uploadDisease(disease)
            // _ ignoress the exception
        } catch (_: Exception){
        }
    }
    suspend fun deleteDisease(disease: DiseaseInfo) {
        diseaseDao.deleteDisease(disease)
        try {
            firebaseService.deleteDisease(disease)
            // _ ignoress the exception
        } catch (_: Exception){
        }

    }

}