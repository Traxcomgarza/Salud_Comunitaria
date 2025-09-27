package com.example.feature_show_diseases.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel

@Composable
fun DiseasesScreen(
    viewModel: DiseaseViewModel,
    onKnowMoreClicked: (Long) -> Unit
) {
    val diseases by viewModel.diseases.collectAsState()
    val outlineColor = Color(0xFF9D9D9D)
    // 1. Define el nuevo color para el texto del botón
    val buttonTextColor = Color(0xFF38558D)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            color = Color(0xFF38558D)
        ) {}

        Column(modifier = Modifier.padding(16.dp)) {

            // Fila para el título y el botón
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enfermedades",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = {
                        viewModel.toggleAlphabeticalSort()
                    },
                    // El borde sigue usando el color original
                    border = BorderStroke(1.dp, outlineColor),
                    // 2. Usa el nuevo color solo para el contenido (texto)
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = buttonTextColor
                    )
                ) {
                    Text("Ordenar de A-Z")
                }
            }
            // Lista de enfermedades
            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(diseases) { disease ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onKnowMoreClicked(disease.id) },
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, outlineColor),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = disease.name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Ver más",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}