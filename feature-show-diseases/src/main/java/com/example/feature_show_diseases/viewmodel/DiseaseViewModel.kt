package com.example.feature_show_diseases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.repository.DiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//ViewModel Diseases it can be used by adminUser
class DiseaseViewModel(private val repository: DiseaseRepository) : ViewModel() {
    // list of diseases
    private val _diseases = MutableStateFlow<List<DiseaseInfo>>(emptyList())
    val diseases: StateFlow<List<DiseaseInfo>> = _diseases.asStateFlow()

    fun addDisease(disease: DiseaseInfo) {
        viewModelScope.launch { repository.insertDisease(disease) }
    }

    fun updateDisease(disease: DiseaseInfo) {
        viewModelScope.launch { repository.updateDisease(disease) }
    }

    fun deleteDisease(disease: DiseaseInfo) {
        viewModelScope.launch { repository.deleteDisease(disease) }
    }

    fun getDiseaseById(id: Long): DiseaseInfo? {
        return diseases.value.find { it.id == id }
    }
}