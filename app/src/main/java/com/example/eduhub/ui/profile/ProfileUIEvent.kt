package com.example.eduhub.ui.profile

sealed class ProfileUIEvent {
    data class ShowSnackbar(val message: String) : ProfileUIEvent()
    object NavigateToEditProfile : ProfileUIEvent()
    object NavigateToLogin : ProfileUIEvent()
    object NavigateToProfile: ProfileUIEvent()
}