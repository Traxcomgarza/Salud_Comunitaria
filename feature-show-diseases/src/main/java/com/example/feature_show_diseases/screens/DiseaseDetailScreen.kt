package com.example.feature_show_diseases.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.isEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(
    diseaseId: Long,
    viewModel: DiseaseViewModel,
    onBackClicked: () -> Unit
) {
    // 1. Observa el flujo de datos del ViewModel.
    // `diseasesState` se actualizará automáticamente cuando los datos lleguen a Room.
    val diseasesState by viewModel.diseases.collectAsState()

    //Busca la enfermedad usando el ID
    val disease = diseasesState.find { it.id == diseaseId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(disease?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (diseasesState.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            //Muestra los detalles si la enfermedad se encuentra
        } else if (disease != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Muestra la Descripción
                Text("Descripción", style = MaterialTheme.typography.titleLarge)
                Text(disease.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Muestra los Síntomas
                Text("Síntomas", style = MaterialTheme.typography.titleLarge)
                Text(disease.symptoms, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Muestra la Prevención
                Text("Prevención", style = MaterialTheme.typography.titleLarge)
                Text(disease.prevention, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Muestra el Tratamiento
                Text("Tratamiento", style = MaterialTheme.typography.titleLarge)
                Text(disease.treatment, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            // Muestra un mensaje por si no se encuentra la enfermedad
            Column(modifier = Modifier.padding(paddingValues)) {
                Text("No se encontró la enfermedad con ID: $diseaseId")
            }
        }
    }
}