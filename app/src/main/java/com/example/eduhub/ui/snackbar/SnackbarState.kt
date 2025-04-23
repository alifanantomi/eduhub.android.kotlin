package com.example.eduhub.ui.snackbar

import androidx.compose.material3.SnackbarDuration

data class SnackbarState(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val onAction: (() -> Unit)? = null
)
