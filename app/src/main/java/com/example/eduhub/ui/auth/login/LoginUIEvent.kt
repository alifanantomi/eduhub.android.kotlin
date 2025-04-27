package com.example.eduhub.ui.auth.login

sealed class LoginUIEvent {
    data class ShowSnackbar(val message: String) : LoginUIEvent()
    object NavigateToHome : LoginUIEvent()
    object NavigateToLogin : LoginUIEvent()
}