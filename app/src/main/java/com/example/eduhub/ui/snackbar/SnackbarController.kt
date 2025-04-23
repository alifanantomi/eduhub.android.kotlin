package com.example.eduhub.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope

class SnackbarController(
    private val scope: CoroutineScope
) {
    private val _snackbarMessage = mutableStateOf<SnackbarState?>(null)
    val snackbarMessage: State<SnackbarState?> = _snackbarMessage

    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _snackbarMessage.value = SnackbarState(message, actionLabel, duration)
    }

    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onAction: () -> Unit
    ) {
        _snackbarMessage.value = SnackbarState(message, actionLabel, duration, onAction)
    }

    fun snackbarShown() {
        _snackbarMessage.value = null
    }
}