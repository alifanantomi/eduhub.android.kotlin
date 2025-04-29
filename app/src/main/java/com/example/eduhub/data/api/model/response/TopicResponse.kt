package com.example.eduhub.data.api.model.response

data class TopicItem(
    val id: String,
    val name: String,
    val icon: String
)

data class TopicResponse(
    val data: List<TopicItem>,
    val message: String,
    val error: String?
)