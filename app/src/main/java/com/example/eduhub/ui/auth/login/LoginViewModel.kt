package com.example.eduhub.ui.auth.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.eduhub.data.repository.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import com.example.eduhub.data.model.Result

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val _uiEvent = MutableSharedFlow<LoginUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            if (state.email.isBlank() || state.password.isBlank()) {
                _uiEvent.emit(LoginUIEvent.ShowSnackbar("Please fill in all fields"))
                return@launch
            }

            state = state.copy(isLoading = true)

            when (val result = authRepository.login(state.email, state.password)) {
                is Result.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.emit(LoginUIEvent.NavigateToHome)
                    _uiEvent.emit(LoginUIEvent.ShowSnackbar("Welcome, ${result.data.name}!"))
                }
                is Result.Error -> {
                    Log.e("LoginViewModel", "Error: $result")

                    state = state.copy(isLoading = false, error = result.exception.message)
                    _uiEvent.emit(LoginUIEvent.ShowSnackbar(result.exception.message.toString()))
                }
                is Result.Loading -> { }
            }
        }
    }
}