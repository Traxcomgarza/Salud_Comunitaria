package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_core.model.Suggestion
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(suggestions: List<Suggestion>)

    @Query("SELECT * FROM suggestion ORDER BY title ASC")
    fun getAllSuggestions(): Flow<List<Suggestion>>

    @Query("SELECT * FROM suggestion WHERE id = :id")
    fun getSuggestionById(id: Long): Flow<Suggestion?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestion(suggestion: Suggestion): Long

    @Update
    suspend fun updateSuggestion(suggestion: Suggestion)

    @Delete
    suspend fun deleteSuggestion(suggestion: Suggestion)
}