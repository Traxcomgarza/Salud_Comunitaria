package com.example.salud_comunitaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.data_core.database.AppDatabase
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.firebase.FirebaseSuggestionService
import com.example.data_core.firebase.FirebaseUserService
import com.example.data_core.repository.DiseaseRepository
import com.example.data_core.repository.MedicalHistoryRepository
import com.example.data_core.repository.SettingsRepository
import com.example.data_core.repository.SuggestionRepository
import com.example.data_core.repository.ThemeMode
import com.example.data_core.repository.UserRepository
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.viewmodel.UserViewModelFactory
import com.example.feature_profile.viewmodel.SettingsViewModel
import com.example.feature_profile.viewmodel.SettingsViewModelFactory
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModel
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModelFactory
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.feature_show_diseases.viewmodel.DiseaseViewModelFactory
import com.example.feature_suggestion.viewmodel.SuggestionViewModel
import com.example.feature_suggestion.viewmodel.SuggestionViewModelFactory
import com.example.salud_comunitaria.ui.navigation.NavBar
import com.example.salud_comunitaria.ui.navigation.NavBarComponent
import com.example.salud_comunitaria.ui.theme.Salud_ComunitariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //DB
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDatabase"

        ).fallbackToDestructiveMigration(true).build()

        //Firebase
        val firebaseDiseaseService= FirebaseDiseaseService()
        val firebaseUserService = FirebaseUserService()
        val firebaseSuggestionService = FirebaseSuggestionService()
        //Repository
        val diseaseRepository = DiseaseRepository(database.diseaseDao(),database.userDao(), firebaseDiseaseService)
        val userRepository = UserRepository(database.userDao(),firebaseUserService)
        val suggestionRepository = SuggestionRepository(database.suggestionDao(), firebaseSuggestionService)
        val settingsRepository = SettingsRepository(applicationContext)
        val medicalHistoryRepository = MedicalHistoryRepository(database.userHistoryDao())

        //viewModelFactory
        val diseaseViewModelFactory = DiseaseViewModelFactory(diseaseRepository)
        val userViewModelFactory = UserViewModelFactory(userRepository)
        val suggestionViewModelFactory = SuggestionViewModelFactory(suggestionRepository)
        val settingsViewModelFactory = SettingsViewModelFactory(settingsRepository)

        //ViewModel
        val diseaseViewModel = ViewModelProvider(this, diseaseViewModelFactory)[DiseaseViewModel::class.java]
        val userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
        val suggestionViewModel = ViewModelProvider(this, suggestionViewModelFactory)[SuggestionViewModel::class.java]
        val settingsViewModel = ViewModelProvider(this, settingsViewModelFactory)[SettingsViewModel::class.java]
        //factory medical
        val medicalHistoryViewModelFactory =
            MedicalHistoryViewModelFactory(medicalHistoryRepository, userViewModel)
        val medicalHistoryViewModel =
            ViewModelProvider(this, medicalHistoryViewModelFactory)[MedicalHistoryViewModel::class.java]

        //Sync
        diseaseViewModel.syncFromFirebase()
        userViewModel.syncFromFirebase()
        suggestionViewModel.syncFromFirebase()


        setContent {
            val themeMode by settingsRepository.themeModeFlow.collectAsState(initial = ThemeMode.LIGHT)
            Salud_ComunitariaTheme(themeMode = themeMode, dynamicColor = false) {
                val navController = rememberNavController()


                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                val firebaseUser by userViewModel.currentUser.collectAsState()

                val hideRoutes = setOf("splash", "login", "signup","diseaseDetail/{diseaseId}")

                val showNavBar = firebaseUser != null && (currentRoute !in hideRoutes)

                Scaffold(
                    bottomBar = {
                        if (showNavBar) {
                            NavBarComponent(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavBar(
                        diseaseViewModel = diseaseViewModel,
                        userViewModel = userViewModel,
                        suggestionViewModel = suggestionViewModel,
                        settingsViewModel = settingsViewModel,
                        medicalHistoryViewModel = medicalHistoryViewModel,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}
