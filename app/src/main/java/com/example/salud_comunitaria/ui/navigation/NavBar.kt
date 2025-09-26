package com.example.salud_comunitaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.screens.LoginScreen
import com.example.feature_auth.screens.SignInScreen
import com.example.feature_show_diseases.screens.DiseaseDetailScreen
import com.example.feature_show_diseases.screens.DiseasesScreen
import com.example.feature_show_diseases.viewmodel.DiseaseDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavBar(
    diseaseViewModel: DiseaseViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "diseases"
    ) {
//        composable("login"){LoginScreen(userViewModel, navController)}
//        composable("register"){SignInScreen()}
//        composable("diseases"){DiseasesScreen()}

        composable("diseases") {
            DiseasesScreen(
                viewModel = diseaseViewModel,
                onKnowMoreClicked = { diseaseId ->
                    navController.navigate("diseaseDetail/$diseaseId")
                }
            )
        }


        composable("diseaseDetail/{diseaseId}") { backStackEntry ->
            val diseaseId = backStackEntry.arguments?.getString("diseaseId")?.toLongOrNull()
            if (diseaseId != null) {
                val detailViewModel: DiseaseDetailViewModel = koinViewModel()
                DiseaseDetailScreen(
                    diseaseId = diseaseId,
                    viewModel = detailViewModel,
                    onBackClicked = { navController.popBackStack() }
                )
            } else {
                println("Error: diseaseId no válido para la pantalla de detalles.")
                navController.popBackStack()
            }
        }
    }
}
