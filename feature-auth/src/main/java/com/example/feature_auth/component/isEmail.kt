package com.example.feature_auth.component

fun isEmail(username: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
}