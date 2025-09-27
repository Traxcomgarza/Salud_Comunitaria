package com.example.feature_admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data_core.model.DiseaseInfo
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.ui_resources.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiseaseScreen(
    viewModel: DiseaseViewModel,
    onBackClicked: () -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var prevention by remember { mutableStateOf("") }
    var treatment by remember { mutableStateOf("") }

    if (snackbarMessage != null) {
        LaunchedEffect(snackbarMessage) {
            snackbarHostState.showSnackbar(snackbarMessage!!)
            snackbarMessage = null
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Añadir Enfermedad") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = symptoms,
                onValueChange = { symptoms = it },
                label = { Text("Síntomas") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = prevention,
                onValueChange = { prevention = it },
                label = { Text("Prevención") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = treatment,
                onValueChange = { treatment = it },
                label = { Text("Tratamiento") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (
                        name.isBlank() || description.isBlank() ||
                        symptoms.isBlank() || prevention.isBlank() || treatment.isBlank()
                    ) {
                        snackbarMessage = "Debe llenar todos los campos"
                    } else {
                        scope.launch {
                            viewModel.addDisease(
                                DiseaseInfo(
                                    name = name,
                                    description = description,
                                    symptoms = symptoms,
                                    prevention = prevention,
                                    treatment = treatment
                                )
                            )
                            snackbarMessage = "Enfermedad añadida correctamente"
                            name = ""
                            description = ""
                            symptoms = ""
                            prevention = ""
                            treatment = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }

}