package com.example.feature_auth.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.feature_auth.viewmodel.UserViewModel

@Composable
fun SignInScreen(
    userViewModel: UserViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var typeOfUser by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Iniciar Sesión", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Usuario") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* TODO  LOGIN */ }
                    ) {
                        Text("Ingresar")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("login") }
                    ) {
                        Text("Iniciar Sesion")
                    }
                }
            }
        }
    }
}