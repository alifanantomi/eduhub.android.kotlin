package com.example.eduhub.ui.auth.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class RegisterViewModel: ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            delay(1000)

            Log.d("name", state.name)
            Log.d("email", state.email)
            Log.d("pass", state.password)

            state = state.copy(isLoading = false, error = null)
        }
    }
}
