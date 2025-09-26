package com.example.data_core.repository

import com.example.data_core.dao.DiseaseDao
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.model.DiseaseInfo
import kotlinx.coroutines.flow.Flow
import kotlin.collections.toTypedArray

class DiseaseRepository(
    private val diseaseDao: DiseaseDao,
    private val firebaseService: FirebaseDiseaseService
) {

    fun getAllDiseases(): Flow<List<DiseaseInfo>> {
        return diseaseDao.getAllDiseases()
    }

    fun getDiseaseById(id: Long): Flow<DiseaseInfo?> {
        return diseaseDao.getDiseaseById(id)
    }

    suspend fun insertDisease(disease: DiseaseInfo) {
        diseaseDao.insertDisease(disease)
    }

    suspend fun updateDisease(disease: DiseaseInfo) {
        diseaseDao.updateDisease(disease)
    }

    suspend fun deleteDisease(disease: DiseaseInfo) {
        diseaseDao.deleteDisease(disease)
    }

    suspend fun syncFromFirebase() {
        try {
            val diseasesFromFirebase = firebaseService.getAllDiseases()
            diseaseDao.insertAll(diseasesFromFirebase)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}