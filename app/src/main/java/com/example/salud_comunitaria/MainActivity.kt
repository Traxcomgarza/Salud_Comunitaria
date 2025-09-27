package com.example.salud_comunitaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.data_core.database.AppDatabase
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.firebase.FirebaseUserService
import com.example.data_core.repository.DiseaseRepository
import com.example.data_core.repository.UserRepository
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.viewmodel.UserViewModelFactory
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModel
import com.example.feature_medical_history.viewmodel.MedicalHistoryViewModelFactory
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.feature_show_diseases.viewmodel.DiseaseViewModelFactory
import com.example.salud_comunitaria.ui.navigation.NavBar
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

        ).fallbackToDestructiveMigration().build()

        //Firebase
        val firebaseDiseaseService= FirebaseDiseaseService()
        val firebaseUserService = FirebaseUserService()
        //Repository
        val diseaseRepository = DiseaseRepository(database.diseaseDao(),database.userDao(), firebaseDiseaseService)
        val userRepository = UserRepository(database.userDao(),firebaseUserService)
        //viewModelFactory
        val diseaseViewModelFactory = DiseaseViewModelFactory(diseaseRepository)
        val userViewModelFactory = UserViewModelFactory(userRepository)
        val medicalHistoryViewModelFactory = MedicalHistoryViewModelFactory(userRepository)
        //ViewModel
        val diseaseViewModel = ViewModelProvider(this, diseaseViewModelFactory)[DiseaseViewModel::class.java]
        val userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
        val medicalHistoryViewModel = ViewModelProvider(this, medicalHistoryViewModelFactory)[MedicalHistoryViewModel::class.java]

        //Sync
        diseaseViewModel.syncFromFirebase()
        userViewModel.syncFromFirebase()

        setContent {
            Salud_ComunitariaTheme {
                NavBar(
                    diseaseViewModel = diseaseViewModel,
                    userViewModel = userViewModel,
                    medicalHistoryViewModel = medicalHistoryViewModel
                )
            }
        }
    }
}
