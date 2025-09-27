package com.example.feature_profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data_core.repository.ThemeMode
import com.example.feature_profile.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val mode by viewModel.themeMode.collectAsState(initial = ThemeMode.LIGHT)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Configuración") }) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ListItem(
                    headlineContent = { Text("Modo oscuro") },
                    supportingContent = { Text("Activa el tema oscuro. Si está apagado, se usa el tema claro.") },
                    trailingContent = {
                        Switch(
                            checked = mode == ThemeMode.DARK,
                            onCheckedChange = viewModel::setDarkEnabled
                        )
                    }
                )
            }
        }
        }
    }
}