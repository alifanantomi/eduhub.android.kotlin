package com.example.eduhub.ui.modules.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.model.Result
import com.example.eduhub.data.repository.ModuleRepository
import com.example.eduhub.ui.modules.list.CreatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModuleDetailViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    var state by mutableStateOf(DetailModuleState())
        private set

    private var fetchJob: Job? = null

    fun loadDetailModule(moduleId: String) {
        fetchJob?.cancel()
        state = state.copy(isLoading = true, error = null)

        fetchJob = viewModelScope.launch {
            when (val result = moduleRepository.getModuleById(moduleId)) {
                is Result.Success -> {
                    val detailModule = DetailModuleState(
                        module = ModuleState(
                            id = result.data.data.id,
                            title = result.data.data.title,
                            image = result.data.data.imageUrl.toString(),
                            summary = result.data.data.summary,
                            content = result.data.data.content,
                            createdBy = CreatorState(
                                id = result.data.data.createdBy.id,
                                name = result.data.data.createdBy.name,
                                image = result.data.data.createdBy.image
                            )
                        )
                    )

                    state = state.copy(
                        isLoading = false,
                        module = detailModule.module,
                        error = null
                    )
                }
                is Result.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
                is Result.Loading -> {
                    // Handle loading
                }
            }
        }
    }
}