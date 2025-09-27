package com.example.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data_core.model.User
import com.example.data_core.model.UserDiseaseCrossRef
import com.example.data_core.model.UserWithDiseases // This will now be used
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //Read Users
    @Query("SELECT * FROM users_table ORDER BY id DESC")
    fun getAllUsers(): Flow<List<User>>

    //getByID
    @Query("SELECT * FROM users_table WHERE id = :id")
    fun getUserById(id: Long): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    //Put
    @Update
    suspend fun updateUser(user: User)

    //Delete
    @Delete
    suspend fun deleteUser(user: User)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserDiseaseLink(relation: UserDiseaseCrossRef)

    @Transaction
    @Query("SELECT * FROM users_table WHERE id = :userId")
    fun getUserWithDiseases(userId: Long): Flow<UserWithDiseases?> // Added this function
}
