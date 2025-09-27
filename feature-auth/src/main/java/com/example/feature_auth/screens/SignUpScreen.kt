package com.example.feature_auth.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data_core.model.User
import com.example.feature_auth.component.isEmail
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.ui_resources.R
import com.example.ui_resources.theme.Inter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    userViewModel: UserViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Handle snackbar message and reset it, inputs
    if (snackbarMessage != null) {
        LaunchedEffect(snackbarMessage) {
            snackbarHostState.showSnackbar(snackbarMessage!!)
            snackbarMessage = null
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = Inter)
                    )
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
                    Button(onClick = {
                        if (username.isEmpty() || password.isEmpty()) {
                            snackbarMessage = "Debe llenar todos los campos"
                        }
                        else if (!isEmail(username)) {
                            snackbarMessage = "Ingresa un correo válido"
                        }

                        else {

                            val user = User(
                                    username = username,
                                    password = password,
                                    userType = "standard"
                                )
                            val result = userViewModel.addUser(user)
                            if (result){
                                snackbarMessage = "Usuario registrado exitosamente"
                                scope.launch {
                                    delay(1000L)
                                    navController.navigate("login")
                                }
                            }
                            else{
                                snackbarMessage = "El usuario ya existe"
                            }



                        }
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0965C2)),
                        shape = RoundedCornerShape(4.dp),

                        ) {
                        Text("Registrarse")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {navController.navigate("login") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0965C2)),
                        shape = RoundedCornerShape(4.dp),

                        ) {
                        Text("Iniciar Sesion")
                    }
                }
            }
        }
    }
}