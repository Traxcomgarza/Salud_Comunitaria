package com.example.feature_show_diseases.viewmodel

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.repository.DiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

data class DiseaseDetailUiState(
    val disease: DiseaseInfo? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class DiseaseDetailViewModel(private val repository: DiseaseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DiseaseDetailUiState())
    val uiState: StateFlow<DiseaseDetailUiState> = _uiState.asStateFlow()

    fun loadDiseaseDetails(diseaseId: Long) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                repository.getDiseaseById(diseaseId)
                    .filterNotNull()
                    .collect { disease ->
                        _uiState.value = DiseaseDetailUiState(disease = disease, isLoading = false)
                    }
            } catch (e: Exception) {
                _uiState.value = DiseaseDetailUiState(
                    error = "Error al cargar los detalles: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}