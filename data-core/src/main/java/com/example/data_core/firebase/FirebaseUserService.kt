package com.example.data_core.firebase

import com.example.data_core.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserService (
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

){
    //USERS FIREBASE
    private val collection = firestore.collection("user")

    suspend fun uploadUser(user: User){
        collection.document(user.id.toString()).set(user.toMap()).await()
    }

    suspend fun deleteUser(user: User){
        collection.document(user.id.toString()).delete().await()

    }

    suspend fun getAllUsers(): List<User>{
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull {
            it.data?.let { data -> User.fromMap(data) }
        }



    }




}