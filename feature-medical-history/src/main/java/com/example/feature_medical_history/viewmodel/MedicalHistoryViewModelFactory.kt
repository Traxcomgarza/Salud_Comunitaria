package com.example.feature_medical_history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_core.repository.MedicalHistoryRepository
import com.example.feature_auth.viewmodel.UserViewModel

class MedicalHistoryViewModelFactory(
    private val repository: MedicalHistoryRepository,
    private val userViewModel: UserViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicalHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicalHistoryViewModel(repository, userViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}