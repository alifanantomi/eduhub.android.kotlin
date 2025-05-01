package com.example.eduhub.ui.modules.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.repository.ModuleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.eduhub.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ModuleListViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository
): ViewModel() {
    var state by mutableStateOf(ModuleListState())
        private set

    private var fetchJob: Job? = null

    init {
        loadModules()
    }


    fun loadModules() {
        fetchJob?.cancel()
        state = state.copy(isLoading = true, error = null)

        fetchJob = viewModelScope.launch {
            when (val result = moduleRepository.getAllModules()) {
                is Result.Success -> {
                    val moduleItems = result.data.data.map {
                        ModuleItemState(
                            id = it.id,
                            title = it.title,
                            summary = it.summary,
                            image = it.imageUrl.toString(),
                            createdBy = CreatorState(
                                id = it.createdBy.id,
                                name = it.createdBy.name,
                                image = it.createdBy.image
                            )
                        )
                    }

                    state = state.copy(
                        isLoading = false,
                        modules = moduleItems,
                        filteredModules = moduleItems,
                        error = null
                    )
                }
                is Result.Error -> {
                    Log.e("ModuleListViewModel", "Error: ${result.exception.message}")
                    state = state.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
                is Result.Loading -> { }
            }

        }
    }
}
