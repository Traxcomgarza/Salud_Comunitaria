package com.example.feature_auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.dao.UserDao
import com.example.data_core.model.User
import com.example.data_core.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first


class UserViewModel(private val repository: UserRepository): ViewModel(){
    //List of Users it can be used for adminUser
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    //Current User
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        viewModelScope.launch {
            repository.getAllUsers().collectLatest { userList ->
                _users.value = userList
            }
        }
    }

    fun addUser(user: User): Boolean {
        val alreadyExist = users.value.any { it.username == user.username }
        if (!alreadyExist) {
            viewModelScope.launch { repository.insertUser(user) }
            return true
        }else{
            return false
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch { repository.updateUser(user) }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch { repository.deleteUser(user) }
    }

    fun getUserById(id: Long): User? {
        return users.value.find { it.id == id }
    }
    fun syncFromFirebase(){
        viewModelScope.launch { repository.syncFromFirebase() }
    }

    fun authenticate(username: String, password: String): User? {
        val user = users.value.find { it.username == username && it.password == password }
        _currentUser.value = user
        return user
    }



}