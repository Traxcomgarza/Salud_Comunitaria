package com.example.feature_medical_history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MedicalHistoryViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    private val userId = 40L // <<--- VOLVEMOS AL ID HARCODEADO

    val medicalHistory: StateFlow<List<DiseaseInfo>> =
        userRepository.getUserMedicalHistory(userId) // Usa el userId fijo
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}

class MedicalHistoryViewModelFactory(
    private val userRepository: UserRepository
    // El userId ya no se pasa aquí
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicalHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Ya no se pasa userId al constructor del ViewModel
            return MedicalHistoryViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}