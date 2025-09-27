package com.example.data_core.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "app_settings"

val Context.settingsDataStore by preferencesDataStore(name = STORE_NAME)

enum class ThemeMode { LIGHT, DARK }

class SettingsRepository(private val context: Context) {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val themeModeFlow: Flow<ThemeMode> =
        context.settingsDataStore.data.map { prefs ->
            val raw = prefs[Keys.THEME_MODE] ?: ThemeMode.LIGHT.name
            runCatching { ThemeMode.valueOf(raw) }.getOrDefault(ThemeMode.LIGHT)
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }
}