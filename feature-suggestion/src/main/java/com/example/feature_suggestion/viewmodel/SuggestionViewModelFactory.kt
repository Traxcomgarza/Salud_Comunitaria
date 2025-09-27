package com.example.feature_suggestion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_core.repository.SuggestionRepository

class SuggestionViewModelFactory(
    private val repository: SuggestionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuggestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SuggestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}