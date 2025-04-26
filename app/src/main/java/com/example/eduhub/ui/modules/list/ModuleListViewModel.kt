package com.example.eduhub.ui.modules.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.repositories.ModuleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ModuleListViewModel(
    private val moduleRepository: ModuleRepository
): ViewModel() {
    var state by mutableStateOf(ModuleListState())
        private set

    private var fetchJob: Job? = null

    fun loadModules() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val moduleItems = moduleRepository.getAllModules()
                state = state.copy(
                    isLoading = false,
                    modules = moduleItems,
                    filteredModules = moduleItems
                )
            } catch (e: Exception) {
                Log.e("Failed to load modules", e.message.toString())
                state = state.copy(
                    isLoading = false,
                    error = "Failed to load modules: ${e.message}"
                )
            }

        }
    }
}
