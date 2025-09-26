package com.example.salud_comunitaria

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin activity
            androidLogger()
            // Provide Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(appModule)
        }
    }
}