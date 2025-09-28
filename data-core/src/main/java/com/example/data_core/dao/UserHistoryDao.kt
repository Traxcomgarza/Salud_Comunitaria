package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.UserHistoryEntry
import kotlinx.coroutines.flow.Flow
@Dao
interface UserHistoryDao {

    @Query("""
        SELECT d.*
        FROM disease_info_table d
        INNER JOIN user_history h ON d.disease_info_id = h.disease_id_fk
        WHERE h.uid = :uid
        ORDER BY d.name COLLATE NOCASE ASC
    """)
    fun getHistoryByUid(uid: String): Flow<List<DiseaseInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entry: UserHistoryEntry): Long
}