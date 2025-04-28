package com.example.eduhub.data.repository

import com.example.eduhub.data.api.model.response.RegisterResponse
import com.example.eduhub.data.model.User
import com.example.eduhub.data.model.Result

interface AuthRepositoryInterface {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse>
    suspend fun logout()
    fun isLoggedIn(): Boolean
}

