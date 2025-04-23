package com.example.eduhub.ui.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object AppSnackbarController {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    val controller = SnackbarController(scope)
}