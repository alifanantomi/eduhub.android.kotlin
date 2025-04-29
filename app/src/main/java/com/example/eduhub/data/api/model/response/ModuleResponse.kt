package com.example.eduhub.data.api.model.response

data class Creator(
    val id: String,
    val name: String,
    val image: String?
)

data class ModuleItem(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val summary: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val createdBy: Creator
)

data class ModuleResponse(
    val data: List<ModuleItem>,
    val message: String,
    val error: String?
)

data class AuthorResponse(
    val id: String,
    val name: String,
    val imageUrl: String?
)

data class ModuleDetail(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val summary: String,
    val content: String,
    val author: AuthorResponse,
)
