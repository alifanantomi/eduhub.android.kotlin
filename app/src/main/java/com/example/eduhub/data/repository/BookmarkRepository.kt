package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.model.request.BookmarkRequest
import com.example.eduhub.data.api.model.response.BookmarkResponse
import com.example.eduhub.data.model.Result
import org.json.JSONObject
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val apiService: ApiService,
) : BookmarkRepositoryInterface {

    override suspend fun getAllBookmarks(): Result<BookmarkResponse> {
        return try {
            val response = apiService.getBookmarks()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Failed to fetch bookmarks"))
            } else {
                val errorJson = response.errorBody()?.string()

                val errorMessage = if (errorJson != null) {
                    try {
                        val jsonObject = JSONObject(errorJson)
                        jsonObject.getString("error")
                    } catch (e: Exception) {
                        "Unknown error"
                    }
                } else {
                    "Unknown error"
                }

                Result.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun createBookmark(moduleId: String): Result<BookmarkResponse> {
        return try {
            val response = apiService.createBookmark(BookmarkRequest(moduleId))

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Failed to create bookmark"))
            } else {
                Result.Error(Exception("Failed to create bookmark"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
