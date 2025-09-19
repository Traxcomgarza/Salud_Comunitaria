package com.example.data_core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_core.model.User
import com.example.data_core.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel(){
    //List of Users it can be used for adminUser
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

//    init {
//        viewModelScope.launch {
//            repository.getAllUsers().collectLatest { userList ->
//                _users.value = userList
//            }
//        }
//    }

    fun addUser(user: User) {
        viewModelScope.launch { repository.insertUser(user) }
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
}