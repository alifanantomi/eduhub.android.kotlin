package com.example.eduhub.data.mapper

import com.example.eduhub.data.api.model.response.UserDto
import com.example.eduhub.data.model.User
import com.example.eduhub.data.model.UserRole

fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        name = name,
        image = image ?: "",
        emailVerified = emailVerified,
//        role = when (role.uppercase()) {
//            "ADMIN" -> UserRole.ADMIN
//            else -> UserRole.USER
//        }
    )
}