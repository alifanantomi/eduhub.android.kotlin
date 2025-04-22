package com.example.eduhub.ui.auth.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
