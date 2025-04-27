package com.example.eduhub.data.repository

import com.example.eduhub.data.model.User
import com.example.eduhub.data.model.Result

interface AuthRepositoryInterface {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String, name: String): Result<User>
    suspend fun logout()
    fun isLoggedIn(): Boolean
}

