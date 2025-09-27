package com.example.salud_comunitaria.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarComponent(
    navController: NavController
){
    var expanded by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color(0xFFF8F7FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate("diseases") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "diseases",
                    tint = Color(0xFF8C8CB1)
                )
            }
            IconButton(onClick = {navController.navigate("suggestion") }) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = "suggestion",
                    tint = Color(0xFF3D4CFF)
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3D4CFF))
                    .zIndex(2f)
            ) {
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Apps,
                        contentDescription = if (expanded) "Cerrar opciones" else "Agregar",
                        tint = Color.White
                    )
                }
            }

            IconButton(onClick = { navController.navigate("admin")  }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "adminDiseases",
                    tint = Color(0xFF8C8CB1)
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "profile",
                    tint = Color(0xFF8C8CB1)
                )
            }
        }

        if (expanded) {
            val popupLiftDp = 160.dp
            val yOffset = with(LocalDensity.current) { (-popupLiftDp).roundToPx() }

            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(0, yOffset),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Card(
                    shape = RoundedCornerShape(40.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    modifier = Modifier
                        .width(340.dp)
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("settings")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Configuración")
                        }

                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("diseases")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver Enfermedades")
                        }

                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("profile")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Perfil")
                        }

                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("suggestion")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver Sugerencias")
                        }

                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("adminSuggestion")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Añadir Sugerencias")
                        }

                        Button(
                            onClick = {
                                expanded = false
                                navController.navigate("admin")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar Enfermedad")
                        }

                    }
                }
            }
        }
    }
}