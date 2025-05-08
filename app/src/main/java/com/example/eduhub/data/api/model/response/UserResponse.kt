package com.example.eduhub.data.api.model.response

import com.example.eduhub.data.model.UserRole

data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val image: String?,
    val role: UserRole,
    val emailVerified: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class UpdateUserResponse(
    val data: UserResponse
)