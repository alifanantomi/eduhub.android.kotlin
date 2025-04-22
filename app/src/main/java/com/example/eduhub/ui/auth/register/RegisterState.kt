package com.example.eduhub.ui.auth.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
