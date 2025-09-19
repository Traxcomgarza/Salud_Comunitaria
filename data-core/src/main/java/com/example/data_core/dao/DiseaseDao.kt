package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_core.model.Disease_Info
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM disease")
    fun getAllDiseases(): Flow<List<Disease_Info>>

    @Query("SELECT * FROM disease WHERE id = :id")
    fun getDiseaseById(id: Long): Flow<Disease_Info?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisease(disease: Disease_Info)

    @Update
    suspend fun updateDisease(disease: Disease_Info)

    @Delete
    suspend fun deleteDisease(disease: Disease_Info)
}