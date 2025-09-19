package com.example.data_core.repository

import com.example.data_core.dao.UserDao
import com.example.data_core.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao){
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    fun getUserById(id: Long): Flow<List<User?>> = userDao.getUserById(id)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user:User) = userDao.updateUser(user)
    suspend fun deleteUser(user:User) = userDao.deleteUser(user)


}