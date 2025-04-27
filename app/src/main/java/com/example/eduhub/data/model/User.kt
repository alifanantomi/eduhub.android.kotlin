package com.example.eduhub.data.model

enum class UserRole {
    USER,
    ADMIN
}

data class User(
    val id: String,
    val email: String,
    val name: String,
//    val role: UserRole = UserRole.USER,
    val image: String? = null,
    val emailVerified: Boolean = false
)
