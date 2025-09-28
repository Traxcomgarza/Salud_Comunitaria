package com.example.feature_show_diseases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.repository.DiseaseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//ViewModel Diseases it can be used by adminUser
class DiseaseViewModel(private val repository: DiseaseRepository) : ViewModel() {
    // list of diseases
    private val _isSortedAlphabetically = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val diseases: StateFlow<List<DiseaseInfo>> = _isSortedAlphabetically
            .flatMapLatest { isSorted ->
                // The DAO query is already sorted alphabetically, so we just use it.
                repository.getAllDiseases().map { diseaseList ->
                    if (isSorted) {
                        diseaseList
                    } else {
                        diseaseList.shuffled()
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    init {
        viewModelScope.launch {
            repository.refreshDiseases()
        }
    }
    fun toggleAlphabeticalSort() {
        _isSortedAlphabetically.value = !_isSortedAlphabetically.value
    }

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

    suspend fun addDiseaseToHistory(userId: Long, diseaseId: Long): Boolean {
        return repository.addDiseaseToUserHistory(userId, diseaseId)
    }

    fun syncFromFirebase(){
        viewModelScope.launch { repository.syncFromFirebase() }
    }
}