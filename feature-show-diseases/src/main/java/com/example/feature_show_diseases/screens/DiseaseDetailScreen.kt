package com.example.feature_show_diseases.screens

// import androidx.activity.result.launch // No parece usarse
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets // Re-añadida
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars    // Re-añadida
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding // Re-añadida
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
// import androidx.compose.material3.ExperimentalMaterial3Api // Probablemente ya no sea necesaria
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
// import androidx.compose.material3.TopAppBar // Eliminada
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
// import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModel
// import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun DiseaseDetailScreen(
    diseaseId: Long,
    viewModel: DiseaseViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    onBackClicked: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val diseasesState by viewModel.diseases.collectAsState()
    val disease = diseasesState.find { it.id == diseaseId }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF38558D)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .height(75.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = disease?.name ?: "Detalle de la Enfermedad",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            if (disease != null) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val success = medicalHistoryViewModel.addToHistory(disease.id)
                            val message = if (success) {
                                "Enfermedad guardada en el Historial"
                            } else {
                                "Error al guardar la enfermedad. Intentalo de nuevo."
                            }
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                            if (success) onNavigateToHistory()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar al historial")
                }
            }
        }
    ) { paddingValuesScaffold ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValuesScaffold)
        ) {
            if (diseasesState.isEmpty() && disease == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (disease != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Descripcion", style = MaterialTheme.typography.titleLarge)
                    Text(disease.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Sintomas", style = MaterialTheme.typography.titleLarge)
                    Text(disease.symptoms, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Prevencion", style = MaterialTheme.typography.titleLarge)
                    Text(disease.prevention, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Tratamiento", style = MaterialTheme.typography.titleLarge)
                    Text(disease.treatment, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No se encontro la enfermedad con ID: $diseaseId")
                }
            }
        }
    }
}