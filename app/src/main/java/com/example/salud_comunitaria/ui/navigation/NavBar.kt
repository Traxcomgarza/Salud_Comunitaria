package com.example.salud_comunitaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature_admin.screens.AddDiseaseScreen
import com.example.feature_admin.screens.AdminSuggestionScreen
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.screens.LoginScreen
import com.example.feature_auth.screens.SignInScreen
import com.example.feature_show_diseases.screens.DiseaseDetailScreen
import com.example.feature_show_diseases.screens.DiseasesScreen
import com.example.feature_suggestion.screens.SuggestionScreen
import com.example.feature_suggestion.viewmodel.SuggestionViewModel
import com.example.salud_comunitaria.screen.SplashScreen


@Composable
fun NavBar(
    diseaseViewModel: DiseaseViewModel,
    userViewModel: UserViewModel,
    suggestionViewModel: SuggestionViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "suggestion"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(userViewModel, navController) }
        composable("signup") { SignInScreen(userViewModel, navController) }

        composable("diseases") {DiseasesScreen(viewModel = diseaseViewModel,onKnowMoreClicked = { diseaseId ->
                    navController.navigate("diseaseDetail/$diseaseId")
                }
            )
        }

        composable(
            route = "diseaseDetail/{diseaseId}",
            arguments = listOf(navArgument("diseaseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val diseaseId = backStackEntry.arguments?.getLong("diseaseId") ?: -1L
            DiseaseDetailScreen(
                diseaseId = diseaseId,
                viewModel = diseaseViewModel,
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable("admin"){ AddDiseaseScreen(viewModel = diseaseViewModel, onBackClicked = { navController.popBackStack() })}
        composable("suggestion"){SuggestionScreen( viewModel = suggestionViewModel, navController = navController)}
        composable("adminSuggestion"){ AdminSuggestionScreen(viewModel = suggestionViewModel, onBackClicked = { navController.popBackStack() }) }
    }
}
