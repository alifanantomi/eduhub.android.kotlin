package com.example.eduhub.ui.topics

data class TopicItemState(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
)

data class TopicListState(
    val topics: List<TopicItemState> = emptyList(),
    val filteredTopics: List<TopicItemState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

