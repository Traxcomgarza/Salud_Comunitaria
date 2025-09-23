package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.dao.UserDao
import com.example.data_core.model.DiseaseInfo
import com.example.data_core.model.User

@Database(
    entities = [User::class, DiseaseInfo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun disease(): DiseaseDao
}
