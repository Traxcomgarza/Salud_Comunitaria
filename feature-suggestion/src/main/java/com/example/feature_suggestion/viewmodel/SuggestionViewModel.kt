package com.example.feature_suggestion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.Suggestion
import com.example.data_core.repository.SuggestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// ViewModel Suggestions
class SuggestionViewModel(private val repository: SuggestionRepository) : ViewModel() {
    // lista de sugerencias
    private val _suggestions = MutableStateFlow<List<Suggestion>>(emptyList())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions.asStateFlow()

    init {
        viewModelScope.launch {
            repository.refreshSuggestions()
            repository.getAllSuggestions().collectLatest { suggestionList ->
                android.util.Log.d(
                    "DEBUG_DATA",
                    "ViewModel: Se recibio una lista de ${suggestionList.size} sugerencias desde Room."
                )
                _suggestions.value = suggestionList
            }
        }
    }

    fun addSuggestion(suggestion: Suggestion) {
        viewModelScope.launch { repository.insertSuggestion(suggestion) }
    }

    fun updateSuggestion(suggestion: Suggestion) {
        viewModelScope.launch { repository.updateSuggestion(suggestion) }
    }

    fun deleteSuggestion(suggestion: Suggestion) {
        viewModelScope.launch { repository.deleteSuggestion(suggestion) }
    }

    fun getSuggestionById(id: Long): Suggestion? {
        return suggestions.value.find { it.id == id }
    }

    fun syncFromFirebase() {
        viewModelScope.launch { repository.syncFromFirebase() }
    }
}