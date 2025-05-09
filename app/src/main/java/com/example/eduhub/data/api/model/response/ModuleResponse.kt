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
    val readTime: Number,
    val createdAt: String,
    val updatedAt: String,
    val createdBy: Creator
)
data class AuthorResponse(
    val id: String,
    val name: String,
    val image: String?
)

data class ModuleDetail(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val summary: String,
    val content: String,
    val createdBy: AuthorResponse,
)

data class ModuleResponse(
    val data: List<ModuleItem>,
    val message: String,
    val error: String?
)

data class DetailModuleResponse(
    val data: ModuleDetail,
    val message: String,
    val error: String?
)
