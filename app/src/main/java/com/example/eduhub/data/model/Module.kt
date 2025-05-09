package com.example.eduhub.data.model

data class Module(
    val id: String,
    val title: String,
    val imageUrl: String,
    val summary: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)
