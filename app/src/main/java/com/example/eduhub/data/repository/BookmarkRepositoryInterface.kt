package com.example.eduhub.data.repository

import com.example.eduhub.data.api.model.response.BookmarkResponse
import com.example.eduhub.data.model.Result

interface BookmarkRepositoryInterface {
    suspend fun getAllBookmarks(): Result<BookmarkResponse>?
    suspend fun createBookmark(id: String): Result<BookmarkResponse>
}