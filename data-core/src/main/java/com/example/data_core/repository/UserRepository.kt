package com.example.data_core.repository

import com.example.data_core.dao.UserDao
import com.example.data_core.firebase.FirebaseUserService
import com.example.data_core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository(
    private val userDao: UserDao,
    private val firebaseService: FirebaseUserService
){
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()


    fun getUserById(id: Long): Flow<List<User?>> = userDao.getUserById(id)
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
        try {
            //call firebaseUserService
            firebaseService.uploadUser(user)
            // _ ignoress the exception
        } catch (_: Exception){
        }
    }

    suspend fun updateUser(user:User){
        userDao.updateUser(user)
        try {
            firebaseService.uploadUser(user)
            // _ ignoress the exception
        } catch (_: Exception){
        }
    }


    suspend fun deleteUser(user:User){
        userDao.deleteUser(user)
        try {
            firebaseService.deleteUser(user)
            // _ ignoress the exception
        } catch (_: Exception){
        }
    }

    suspend fun syncFromFirebase(){
        try {
            val remoteUsers = firebaseService.getAllUsers()
            val localUsers = userDao.getAllUsers().first()

            remoteUsers.forEach{ remote ->
                if(localUsers.none {it.id == remote.id}){
                    userDao.insertUser(remote)
                }
            }
        }catch (_: Exception){}
    }
//TODO Eliminar
    suspend fun insertFakeUsers(){
        val users = listOf(
            User(username = "user1", password = "password1", userType = "user"),
            User(username = "user2", password = "password2", userType = "user")

        )
        users.forEach {
            userDao.insertUser(it)
            try {
                firebaseService.uploadUser(it)
            }catch (_: Exception){}
        }


    }


}