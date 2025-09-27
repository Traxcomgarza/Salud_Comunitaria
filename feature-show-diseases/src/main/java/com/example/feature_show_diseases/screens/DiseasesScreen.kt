package com.example.feature_show_diseases.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel

@Composable
fun DiseasesScreen(
    viewModel: DiseaseViewModel,
    onKnowMoreClicked: (Long) -> Unit
) {
    // Observa la lista de enfermedades desde el ViewModel
    val diseases by viewModel.diseases.collectAsState()

    androidx.compose.foundation.lazy.LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(diseases) { disease ->
            androidx.compose.material3.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onKnowMoreClicked(disease.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    androidx.compose.material3.Text(text = disease.name)
                }
            }
        }
    }
}