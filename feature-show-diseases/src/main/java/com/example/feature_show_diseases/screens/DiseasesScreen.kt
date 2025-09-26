package com.example.feature_show_diseases.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data_core.model.DiseaseInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
@Composable
fun DiseasesScreen(
    viewModel: DiseaseViewModel,
    onKnowMoreClicked: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()){

        Button(
            onClick = { viewModel.syncFromFirebase() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Sincronizar desde Firebase")
        }
        // Campo para la búsqueda
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Buscar enfermedad...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Maneja el estado de la lista de enfermedades
        if (uiState.diseases.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (uiState.searchQuery.isNotEmpty()) {
                        "No se encontraron resultados para '${uiState.searchQuery}'"
                    } else {
                        "No hay enfermedades registradas."
                    }
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = uiState.diseases, key = { it.id }) { disease ->
                    DiseaseCard(disease = disease, onKnowMoreClicked = onKnowMoreClicked)
                }
            }
        }
    }
}

@Composable
fun DiseaseCard(
    disease: DiseaseInfo,
    onKnowMoreClicked: (Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = disease.diseaseName,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = disease.diseaseInfo.take(100) + "...",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onKnowMoreClicked(disease.id) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Saber más...")
            }
        }
    }
}