package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_core.model.DiseaseInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diseases: List<DiseaseInfo>)


    @Query("SELECT * FROM disease_info_table ORDER BY name ASC")
    fun getAllDiseases(): Flow<List<DiseaseInfo>>

    @Query("SELECT * FROM disease_info_table WHERE disease_info_id = :id")
    fun getDiseaseById(id: Long): Flow<DiseaseInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisease(disease: DiseaseInfo): Long

    @Update
    suspend fun updateDisease(disease: DiseaseInfo)

    @Delete
    suspend fun deleteDisease(disease: DiseaseInfo)
}