package com.example.eduhub.ui.auth.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.eduhub.data.model.Result
import com.example.eduhub.data.repository.AuthRepositoryInterface
import com.example.eduhub.ui.auth.login.LoginUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val _uiEvent = MutableSharedFlow<RegisterUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
                _uiEvent.emit(RegisterUIEvent.ShowSnackbar("Please fill in all fields"))
                return@launch
            }

            state = state.copy(isLoading = true)

            when (val result = authRepository.register(state.name, state.email, state.password)) {
                is Result.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.emit(RegisterUIEvent.NavigateToLogin)
                    _uiEvent.emit(RegisterUIEvent.ShowSnackbar("Successfully registered! Please Login with your credentials"))
                }
                is Result.Error -> {
                    Log.e("RegisterViewModel", "Error: $result")

                    state = state.copy(isLoading = false, error = result.exception.message)
                    _uiEvent.emit(RegisterUIEvent.ShowSnackbar(result.exception.message.toString()))
                }
                is Result.Loading -> { }
            }

            state = state.copy(isLoading = false, error = null)
        }
    }
}
