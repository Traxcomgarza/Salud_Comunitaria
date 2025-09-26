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
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import com.example.salud_comunitaria.ui.navigation.NavBar
import com.example.salud_comunitaria.ui.theme.Salud_ComunitariaTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Salud_ComunitariaTheme {
                val diseaseViewModel: DiseaseViewModel = koinViewModel()
                val userViewModel: UserViewModel = koinViewModel()

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