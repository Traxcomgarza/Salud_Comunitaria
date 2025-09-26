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

    //Se agrego el query para la busqueda de enfermedades
    @Query(""" SELECT * FROM disease WHERE :query = '' OR diseaseName LIKE '%' || :query || '%' ORDER BY diseaseName ASC """)
    fun searchDiseases(query: String): Flow<List<DiseaseInfo>>

    @Query("SELECT * FROM disease")
    fun getAllDiseases(): Flow<List<DiseaseInfo>>

    @Query("SELECT * FROM disease WHERE id = :id")
    fun getDiseaseById(id: Long): Flow<DiseaseInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisease(disease: DiseaseInfo)

    @Update
    suspend fun updateDisease(disease: DiseaseInfo)

    @Delete
    suspend fun deleteDisease(disease: DiseaseInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diseases: List<DiseaseInfo>)

}