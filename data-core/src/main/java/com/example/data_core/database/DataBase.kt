package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.dao.SuggestionDao
import com.example.data_core.dao.UserDao
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.Suggestion
import com.example.data_core.model.User

@Database(
    entities = [User::class, DiseaseInfo::class, Suggestion::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun diseaseDao(): DiseaseDao

    abstract fun suggestionDao(): SuggestionDao


}
