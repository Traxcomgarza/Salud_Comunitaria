package com.example.feature_show_diseases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_core.repository.DiseaseRepository

class DiseaseViewModelFactory (
    private val repository: DiseaseRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiseaseViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DiseaseViewModel(repository)as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }

}