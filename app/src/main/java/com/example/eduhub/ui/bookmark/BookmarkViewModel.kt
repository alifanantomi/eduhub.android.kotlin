package com.example.eduhub.ui.bookmark

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduhub.data.model.Result
import com.example.eduhub.data.repository.BookmarkRepository
import com.example.eduhub.ui.auth.login.LoginUIEvent
import com.example.eduhub.ui.modules.list.CreatorState
import com.example.eduhub.ui.modules.list.ModuleItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    var state by mutableStateOf(BookmarkState())
        private set

    private val _uiEvent = MutableSharedFlow<LoginUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var fetchJob: Job? = null

    init {
        loadBookmark()
    }

    fun loadBookmark() {
        fetchJob?.cancel()
        state = state.copy(isLoading = true)

        fetchJob = viewModelScope.launch {
            when (val result = bookmarkRepository.getAllBookmarks()) {
                is Result.Success -> {
                    val bookmarkItems = result.data.data.map {
                        ModuleItemState(
                            id = it.module.id,
                            title = it.module.title,
                            summary = it.module.summary,
                            image = it.module.imageUrl.toString(),
                            readTime = it.module.readTime,
                            createdBy = CreatorState(
                                id = it.module.createdBy.id,
                                name = it.module.createdBy.name,
                                image = it.module.createdBy.image
                            )
                        )
                    }

                    state = state.copy(
                        isLoading = false,
                        bookmarks = bookmarkItems,
                        error = null
                    )
                }

                is Result.Error -> {
                    Log.e("BookmarkViewModel", "Error: ${result.exception.message}")
                    state = state.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }

                is Result.Loading -> { }
            }
         }
    }

    fun createBookmark(moduleId: String) {
        viewModelScope.launch {
            if (moduleId.isBlank()) {
                _uiEvent.emit(LoginUIEvent.ShowSnackbar("Please select a module"))
                return@launch
            }

            state = state.copy(isLoading = true)

            when (val result = bookmarkRepository.createBookmark(moduleId)) {
                is Result.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.emit(LoginUIEvent.ShowSnackbar("Bookmark created successfully"))
                    loadBookmark()
                }

                is Result.Error -> {
                    Log.e("BookmarkViewModel", "Error: ${result}")
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