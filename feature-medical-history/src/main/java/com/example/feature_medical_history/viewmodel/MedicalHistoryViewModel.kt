package com.example.feature_medical_history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.User
import com.example.data_core.repository.MedicalHistoryRepository
import com.example.data_core.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import com.example.feature_auth.viewmodel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class MedicalHistoryViewModel(
    private val repository: MedicalHistoryRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {

    private val uidFlow = userViewModel.currentUser.map { it?.uid }

    val medicalHistory: StateFlow<List<DiseaseInfo>> =
        uidFlow.flatMapLatest { uid ->
            if (uid == null) emptyFlow()
            else repository.getHistory(uid)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun addToHistory(diseaseId: Long): Boolean {
        val uid = userViewModel.currentUser.value?.uid ?: return false
        return repository.add(uid, diseaseId)
    }
}