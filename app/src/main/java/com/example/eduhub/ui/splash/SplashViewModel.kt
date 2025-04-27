package com.example.eduhub.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.eduhub.data.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    var isLoading by mutableStateOf<Boolean>(true)
        private set

    var isLoggedIn by mutableStateOf<Boolean?>(null)
        private set

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val token = userPreferences.getAuthToken()
            isLoggedIn = token.isNotEmpty()
            isLoading = false
        }
    }
}