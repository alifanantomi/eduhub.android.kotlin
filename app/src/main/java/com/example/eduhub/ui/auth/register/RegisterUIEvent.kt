package com.example.eduhub.ui.auth.register

sealed class RegisterUIEvent {
    data class ShowSnackbar(val message: String) : RegisterUIEvent()
    object NavigateToLogin : RegisterUIEvent()
}