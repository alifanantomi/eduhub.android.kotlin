package com.example.eduhub.data.api.model.response

data class BookmarkItem (
    val id: String,
    val userId: String,
    val moduleId: String,
    val createdAt: String,
    val module: ModuleItem
)

data class BookmarkResponse(
    val data: List<BookmarkItem>,
    val message: String,
    val error: String?
)
