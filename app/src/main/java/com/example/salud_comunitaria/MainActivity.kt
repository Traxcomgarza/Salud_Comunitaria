package com.example.salud_comunitaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.data_core.database.AppDatabase
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.firebase.FirebaseUserService
import com.example.data_core.repository.DiseaseRepository
import com.example.data_core.repository.UserRepository
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_auth.viewmodel.UserViewModelFactory
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

        ).build()
        //Firebase
        val  firebaseDiseaseService= FirebaseDiseaseService()
        val firebaseUserService = FirebaseUserService()
        //Repository
        val diseaseRepository = DiseaseRepository(database.diseaseDao(),firebaseDiseaseService)
        val userRepository = UserRepository(database.userDao(),firebaseUserService)
        //viewModelFactory
        val diseaseViewModelFactory = DiseaseViewModelFactory(diseaseRepository)
        val userViewModelFactory = UserViewModelFactory(userRepository)
        //ViewModel
        val diseaseViewModel = ViewModelProvider(this, diseaseViewModelFactory)[DiseaseViewModel::class.java]
        val userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]


        setContent {
            Salud_ComunitariaTheme {
                NavBar(
                    diseaseViewModel = diseaseViewModel,
                    userViewModel = userViewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Salud_ComunitariaTheme {
        Greeting("Android")
    }
}