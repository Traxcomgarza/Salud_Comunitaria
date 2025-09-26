package com.example.salud_comunitaria

import androidx.room.Room
import com.example.data_core.database.AppDatabase
import com.example.data_core.firebase.FirebaseDiseaseService
import com.example.data_core.firebase.FirebaseUserService
import com.example.data_core.repository.DiseaseRepository
import com.example.data_core.repository.UserRepository
import com.example.feature_auth.viewmodel.UserViewModel
import com.example.feature_show_diseases.viewmodel.DiseaseDetailViewModel
import com.example.feature_show_diseases.viewmodel.DiseaseViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "salud_comunitaria_db")
            .build()
    }

    // Recetas para los "ayudantes" de la base de datos (DAOs)
    single { get<AppDatabase>().diseaseDao() }
    single { get<AppDatabase>().userDao() }

    single { FirebaseDiseaseService() }
    single { FirebaseUserService() }

    single { DiseaseRepository(get(), get()) }
    single { UserRepository(get(), get()) }

    viewModel { DiseaseViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { DiseaseDetailViewModel(get()) }
}