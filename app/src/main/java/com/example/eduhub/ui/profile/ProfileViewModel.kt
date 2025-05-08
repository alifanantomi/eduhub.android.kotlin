package com.example.eduhub.ui.profile

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.model.Result
import com.example.eduhub.data.model.User
import com.example.eduhub.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.io.path.createTempFile

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    var user by mutableStateOf<User?>(null)
        private set

    private val _uiEvent = MutableSharedFlow<ProfileUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    fun onImageChange(image: String) {
        state = state.copy(image = image)
    }

    private fun getFileName(uri: Uri, context: Context): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex("_display_name")
            if (nameIndex >= 0 && it.moveToFirst()) {
                it.getString(nameIndex)
            } else null
        }
    }

    private fun createTempFileFromUri(uri: Uri, context: Context): File {
        val inputStream  = context.contentResolver.openInputStream(uri)
            ?: throw Exception("Failed to open input stream")
        val filename = getFileName(uri, context) ?: "temp_image_${System.currentTimeMillis()}.jpg}"
        val file = File(context.cacheDir, filename)

        FileOutputStream(file).use { outputStream ->
            inputStream.use {
                it.copyTo(outputStream)
            }

        }

        return file
    }

    fun onImageSelected(uri: Uri, context: Context) {
           viewModelScope.launch {
               try {
                   state = state.copy(isLoading = true)

                   val file = createTempFileFromUri(uri, context)

                   when (val result = userRepository.uploadProfileImage(file)) {
                       is Result.Success -> {
                           val imageUrl = result.data
                           onImageChange(imageUrl)
                       }
                       is Result.Error -> {
                           state = state.copy(isLoading = false, error = result.exception.message)
                           _uiEvent.emit(ProfileUIEvent.ShowSnackbar(result.exception.message.toString()))
                       }
                       is Result.Loading -> { }
                   }
               } catch (e: Exception) {
                   Log.e("ProfileViewModel", "Error uploading image", e)
                   state = state.copy(isLoading = false, error = e.message)
               } finally {
                   state = state.copy(isLoading = false)
               }
           }
    }

    init {
        collectUser()
    }

    fun onUpdateProfile() {
        viewModelScope.launch {
            if (state.name.isBlank()) {
                _uiEvent.emit(ProfileUIEvent.ShowSnackbar("Please fill in all fields"))
                return@launch
            }

            state = state.copy(isLoading = true)

            when (val result = userRepository.updateUserProfile(state.name, state.image)) {
                is Result.Success -> {
                    val updatedUser = result.data.data

                    userPreferences.saveUser(
                        User(
                            id = updatedUser.id,
                            name = updatedUser.name,
                            email = updatedUser.email,
                            image = updatedUser.image,
                            role = updatedUser.role,
                        )
                    )

                    state = state.copy(
                        isLoading = false
                    )

                    _uiEvent.emit(ProfileUIEvent.ShowSnackbar("Profile updated successfully"))
                    _uiEvent.emit(ProfileUIEvent.NavigateToProfile)
                }
                is Result.Error -> {
                    Log.e("ProfileViewModel", "Error: $result")

                    state = state.copy(isLoading = false, error = result.exception.message)
                    _uiEvent.emit(ProfileUIEvent.ShowSnackbar(result.exception.message.toString()))
                }
                is Result.Loading -> { }
            }
        }
    }

    private fun collectUser() {
        viewModelScope.launch {
            userPreferences.userFlow
                .catch {
                    user = null
                }
                .collect {
                    user = it
                    state = state.copy(
                        name = it?.name ?: "",
                        image = it?.image ?: "",
                        email = it?.email ?: ""
                    )
                }
        }
    }

    fun logout() {
        try {
            viewModelScope.launch {
                userPreferences.clearUserData()

                user = null

                _uiEvent.emit(ProfileUIEvent.NavigateToLogin)
            }
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error logging out", e)
        }
    }
}