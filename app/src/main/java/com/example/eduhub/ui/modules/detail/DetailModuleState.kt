package com.example.eduhub.ui.modules.detail

import com.example.eduhub.ui.modules.list.CreatorState

data class ModuleState(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val summary: String = "",
    val content: String = "",
    val createdBy: CreatorState = CreatorState()
)

data class DetailModuleState(
    val module: ModuleState = ModuleState(),
    val isLoading: Boolean = false,
    val error: String? = null
)
