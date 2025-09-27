package com.example.salud_comunitaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.screens.LoginScreen
import com.example.feature_auth.screens.SignInScreen
import com.example.feature_medical_history.screens.MedicalHistoryScreen
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModel
import com.example.feature_show_diseases.screens.DiseaseDetailScreen
import com.example.feature_show_diseases.screens.DiseasesScreen
import com.example.salud_comunitaria.screen.SplashScreen


@Composable
fun NavBar(
    diseaseViewModel: DiseaseViewModel,
    userViewModel: UserViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "diseases"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(userViewModel, navController) }
        composable("signup") { SignInScreen(userViewModel, navController) }

        //Se creo la ruta para la pantalla diseases
        composable("diseases") {DiseasesScreen(viewModel = diseaseViewModel,onKnowMoreClicked = { diseaseId ->
                    navController.navigate("diseaseDetail/$diseaseId")
                }
            )
        }

        //Se creo la ruta para la pantalla de detalle
        composable(
            route = "diseaseDetail/{diseaseId}",
            arguments = listOf(navArgument("diseaseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val diseaseId = backStackEntry.arguments?.getLong("diseaseId") ?: -1L
            DiseaseDetailScreen(
                diseaseId = diseaseId,
                viewModel = diseaseViewModel,
                onBackClicked = { navController.popBackStack() },
                onNavigateToHistory = {
                    navController.navigate("medical_history") {
                        popUpTo("diseases")
                    }
                }
            )
        }

        composable("medical_history") {
            MedicalHistoryScreen(
                viewModel = medicalHistoryViewModel,
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}
