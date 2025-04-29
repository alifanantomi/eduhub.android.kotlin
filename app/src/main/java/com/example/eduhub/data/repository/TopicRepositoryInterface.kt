package com.example.eduhub.data.repository

import com.example.eduhub.data.api.model.response.TopicResponse
import com.example.eduhub.data.model.Result

interface TopicRepositoryInterface {
    suspend fun getAllTopics(): Result<TopicResponse>?
}