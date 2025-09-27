package com.example.data_core.repository

import androidx.room.util.copy
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.model.DiseaseInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DiseaseRepository(
    private val diseaseDao: DiseaseDao,
    private val firebaseService: FirebaseDiseaseService
){
    fun getAllDiseases(): Flow<List<DiseaseInfo>> = diseaseDao.getAllDiseases()

    fun getDiseaseById(id: Long): Flow<DiseaseInfo?> = diseaseDao.getDiseaseById(id)
    suspend fun insertDisease(disease: DiseaseInfo){
        //insert in room to get the id
        val generatedId = diseaseDao.insertDisease(disease)
        val diseaseId = disease.copy(id = generatedId)
        try {
            firebaseService.uploadDisease(diseaseId)
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

    suspend fun syncFromFirebase(){
        try {
            val remoteDisease = firebaseService.getAllDiseases()
            val localDisease = diseaseDao.getAllDiseases().first()

            remoteDisease.forEach{ remote ->
                if(localDisease.none {it.id == remote.id}){
                    diseaseDao.insertDisease(remote)
                }
            }
        }catch (_: Exception){}
    }

}