package com.example.eduhub.data.api.model.response

data class LoginResponse(
    val user: UserDto,
    val token: String,
    val redirect: Boolean
)

data class AuthResponse(
    val message: String,
    val data: LoginResponse,
    val error: String?
)

data class RegisterResponse(
    val message: String
)

data class UserDto(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
//    val role: String,
    val image: String?,
    val emailVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
)
