package com.example.feature_show_diseases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.repository.DiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class DiseasesUiState(
    val diseases: List<DiseaseInfo> = emptyList(),
    val searchQuery: String = "",
)

class DiseaseViewModel(private val repository: DiseaseRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<DiseasesUiState> =
        combine(repository.getAllDiseases(), _searchQuery) { diseases, query ->
            val filteredList = if (query.isBlank()) {
                diseases
            } else {
                diseases.filter {
                    it.diseaseName.contains(query, ignoreCase = true)
                }
            }
            DiseasesUiState(
                diseases = filteredList,
                searchQuery = query
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DiseasesUiState()
        )


    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun addDisease(disease: DiseaseInfo) {
        viewModelScope.launch {
            repository.insertDisease(disease)
        }
    }

    fun updateDisease(disease: DiseaseInfo) {
        viewModelScope.launch {
            repository.updateDisease(disease)
        }
    }

    fun deleteDisease(disease: DiseaseInfo) {
        viewModelScope.launch {
            repository.deleteDisease(disease)
        }
    }

    fun syncFromFirebase() {
        viewModelScope.launch {
            repository.syncFromFirebase()
        }
    }
}