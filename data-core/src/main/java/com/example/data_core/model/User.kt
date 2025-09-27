package com.example.data_core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user",
    indices = [Index(value = ["username"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "userType") val userType: String

){
    //Firebase Mapping
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "username" to username,
        "password" to password,
        "userType" to userType
    )
    //build config firebase
    companion object{
        fun fromMap(map: Map<String, Any?>): User = User(
            id = map["id"] as Long,
            username = map["username"] as String,
            password = map["password"] as String,
            userType = map["userType"] as String
        )
    }
}