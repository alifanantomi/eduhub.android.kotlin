package com.example.eduhub.ui.topics

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.eduhub.data.model.Result
import com.example.eduhub.data.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
): ViewModel() {
    var state by mutableStateOf(TopicListState())
        private set

    private var fetchJob: Job? = null

    init {
        loadTopics()
    }

    fun loadTopics() {
        fetchJob?.cancel()
        state = state.copy(isLoading = true, error = null)

        fetchJob = viewModelScope.launch {
            when (val result = topicRepository.getAllTopics()) {
                is Result.Success -> {
                    val topicItems = result.data.data.map {
                        TopicItemState(
                            id = it.id,
                            name = it.name,
                            icon = it.icon
                        )
                    }

                    state = state.copy(
                        isLoading = false,
                        topics = topicItems,
                        filteredTopics = topicItems,
                        error = null
                    )
                }
                is Result.Error -> {
                    Log.e("TopicViewModel", "Error: ${result.exception.message}")
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