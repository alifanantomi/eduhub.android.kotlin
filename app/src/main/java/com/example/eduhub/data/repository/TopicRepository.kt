package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.model.response.TopicResponse
import com.example.eduhub.data.model.Result
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val apiService: ApiService
) : TopicRepositoryInterface {

    override suspend fun getAllTopics(): Result<TopicResponse> {
        return try {
            val response = apiService.getTopics()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Failed to fetch topics"))
            } else {
                Result.Error(Exception("Failed to fetch topics"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}