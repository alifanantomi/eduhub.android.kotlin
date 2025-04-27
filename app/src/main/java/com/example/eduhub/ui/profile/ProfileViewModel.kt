package com.example.eduhub.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.model.User
import com.example.eduhub.ui.auth.login.LoginUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    private val _uiEvent = MutableSharedFlow<LoginUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        collectUser()
    }

    private fun collectUser() {
        viewModelScope.launch {
            userPreferences.userFlow
                .catch {
                    user = null
                }
                .collect {
                    user = it
                }
        }
    }

    fun logout() {
        try {
            viewModelScope.launch {
                userPreferences.clearUserData()

                user = null

                _uiEvent.emit(LoginUIEvent.NavigateToLogin)
            }
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error logging out", e)
        }
    }
}