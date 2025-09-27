package com.example.feature_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.repository.SettingsRepository
import com.example.data_core.repository.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: SettingsRepository
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> =
        repo.themeModeFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ThemeMode.LIGHT
        )

    fun setDarkEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repo.setThemeMode(if (enabled) ThemeMode.DARK else ThemeMode.LIGHT)
        }
    }
}