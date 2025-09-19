package com.example.data_core.repository

import com.example.data_core.dao.DiseaseDao
import com.example.data_core.model.Disease_Info
import kotlinx.coroutines.flow.Flow

class DiseaseRepository(private val diseaseDao: DiseaseDao){
    fun getAllDiseases(): Flow<List<Disease_Info>> = diseaseDao.getAllDiseases()
    fun getDiseaseById(id: Long): Flow<Disease_Info?> = diseaseDao.getDiseaseById(id)
    suspend fun insertDisease(disease: Disease_Info) = diseaseDao.insertDisease(disease)
    suspend fun updateDisease(disease: Disease_Info) = diseaseDao.updateDisease(disease)
    suspend fun deleteDisease(disease: Disease_Info) = diseaseDao.deleteDisease(disease)

}