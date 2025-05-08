package com.example.eduhub.ui.profile

data class ProfileState(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
