package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_core.dao.DiseaseDao
import com.example.data_core.dao.UserDao
import com.example.data_core.model.Disease_Info
import com.example.data_core.model.User

@Database(
    entities = [User::class, Disease_Info::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun disease(): DiseaseDao
}
