package com.example.data_core.repository

import com.example.data_core.dao.UserHistoryDao
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.UserHistoryEntry
import kotlinx.coroutines.flow.Flow

class MedicalHistoryRepository(
    private val userHistoryDao: UserHistoryDao
) {
    fun getHistory(uid: String): Flow<List<DiseaseInfo>> =
        userHistoryDao.getHistoryByUid(uid)

    suspend fun add(uid: String, diseaseId: Long): Boolean {
        return try {
            userHistoryDao.insert(UserHistoryEntry(uid = uid, diseaseInfoId = diseaseId))
            true
        } catch (_: Exception) {
            false
        }
    }
}