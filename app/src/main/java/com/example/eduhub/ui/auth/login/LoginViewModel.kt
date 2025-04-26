package com.example.eduhub.ui.auth.login

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.eduhub.ui.snackbar.AppSnackbarController

class LoginViewModel: ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var _navigateToHome = mutableStateOf(false)
    val navigateToHome: Boolean get() = _navigateToHome.value

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            delay(500)

            Log.d("email", state.email)
            Log.d("pass", state.password)

            AppSnackbarController.controller.showSnackbar(
                message = "Success signing in!",
                duration = SnackbarDuration.Short
            )

            state = state.copy(isLoading = false, error = null)
            _navigateToHome.value = true
        }
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }
}