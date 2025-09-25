package com.example.salud_comunitaria.screen

import android.window.SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.ui_resources.R

@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier

){
    var state by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        when (state) {
            0 -> {
                delay(800)
                state = 1
            }
            1 -> {
                delay(600)
                state = 2
            }
            2 -> {
                delay(400)
                state = 3
            }
            3 -> {
                delay(1000)
                navController.navigate("login"){
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    val logoScale by animateFloatAsState(
        targetValue = when(state) {
            0 -> 1.5f
            else -> 1.0f
        }, label = ""
    )
    val showText = state >= 3

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFF3E5A90)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.corazon),
                contentDescription = null,
                modifier = Modifier.size(64.dp * logoScale)
            )
            AnimatedVisibility(visible = showText) {
                Text("Bienestar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }




}