package com.example.salud_comunitaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data_core.viewmodel.DiseaseViewModel
import com.example.data_core.viewmodel.UserViewModel
import com.example.feature_auth.screens.LogInScreen
import com.example.feature_auth.screens.SignInScreen
import com.example.feature_show_diseases.screens.DiseasesScreen

@Composable
fun NavBar(
    diseaseViewModel: DiseaseViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = "login"
    ){
        composable("login"){LogInScreen()}
        composable("register"){SignInScreen()}
        composable("diseases"){DiseasesScreen()}


    }
}
