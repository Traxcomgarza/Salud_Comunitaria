package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_core.model.Disease_Info
import com.example.data_core.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //Read Users
    @Query("SELECT * FROM user ORDER by id DESC")
    fun getAllUsers(): Flow<List<User>>

    //getByID
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): Flow<List<User?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)


     //Put
    @Update
    suspend fun updateUser(user: User)

    //Delete
    @Delete
    suspend fun deleteUser(user: User)



}