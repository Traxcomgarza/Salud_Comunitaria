package com.example.data_core.repository

import android.util.Log
import com.example.data_core.dao.SuggestionDao
import com.example.data_core.firebase.FirebaseSuggestionService
import com.example.data_core.model.Suggestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SuggestionRepository(
    private val suggestionDao: SuggestionDao,
    private val firebaseService: FirebaseSuggestionService
){
    suspend fun refreshSuggestions() {
        try {
            val remoteSuggestions = firebaseService.getAllSuggestions()
            Log.d("DEBUG_DATA", "Repository: Se recibieron ${remoteSuggestions.size} sugerencias desde el servicio.")
            suggestionDao.insertAll(remoteSuggestions)
        } catch (e: Exception) {
            Log.e("SuggestionRepository", "Fallo al sincronizar las sugerencias desde el servidor", e)
        }
    }

    fun getAllSuggestions(): Flow<List<Suggestion>> = suggestionDao.getAllSuggestions()

    fun getSuggestionById(id: Long): Flow<Suggestion?> = suggestionDao.getSuggestionById(id)

    suspend fun insertSuggestion(suggestion: Suggestion){
        // Insert in Room to get the id
        val generatedId = suggestionDao.insertSuggestion(suggestion)
        val suggestionId = suggestion.copy(id = generatedId)
        try {
            firebaseService.uploadSuggestion(suggestionId)
        } catch (_: Exception){
            // Ignora la excepción
        }
    }

    suspend fun updateSuggestion(suggestion: Suggestion) {
        suggestionDao.updateSuggestion(suggestion)
        try {
            firebaseService.uploadSuggestion(suggestion)
        } catch (_: Exception){
            // Ignora la excepción
        }
    }

    suspend fun deleteSuggestion(suggestion: Suggestion) {
        suggestionDao.deleteSuggestion(suggestion)
        try {
            firebaseService.deleteSuggestion(suggestion)
        } catch (_: Exception){
            // Ignora la excepción
        }
    }

    suspend fun syncFromFirebase(){
        try {
            val remoteSuggestions = firebaseService.getAllSuggestions()
            val localSuggestions = suggestionDao.getAllSuggestions().first()

            remoteSuggestions.forEach { remote ->
                if(localSuggestions.none { it.id == remote.id }) {
                    suggestionDao.insertSuggestion(remote)
                }
            }
        } catch (_: Exception) {}
    }
}