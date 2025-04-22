package com.example.eduhub.ui.auth.register

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val dob: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
