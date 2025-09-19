package com.example.data_core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.Disease_Info
import com.example.data_core.repository.DiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//ViewModel Diseases it can be used by adminUser
class DiseaseViewModel(private val repository: DiseaseRepository) : ViewModel() {
    // list of diseases
    private val _diseases = MutableStateFlow<List<Disease_Info>>(emptyList())
    val diseases: StateFlow<List<Disease_Info>> = _diseases.asStateFlow()

    fun addDisease(disease: Disease_Info) {
        viewModelScope.launch { repository.insertDisease(disease) }
    }

    fun updateDisease(disease: Disease_Info) {
        viewModelScope.launch { repository.updateDisease(disease) }
    }

    fun deleteDisease(disease: Disease_Info) {
        viewModelScope.launch { repository.deleteDisease(disease) }
    }

    fun getDiseaseById(id: Long): Disease_Info? {
        return diseases.value.find { it.id == id }
    }
}