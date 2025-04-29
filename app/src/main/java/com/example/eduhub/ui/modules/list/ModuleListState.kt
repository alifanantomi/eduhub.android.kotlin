package com.example.eduhub.ui.modules.list

data class ModuleItemState(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val summary: String = "",
    val content: String = "",
    val createdBy: CreatorState = CreatorState()
)

data class CreatorState(
    val id: String = "",
    val name: String = "",
    val image: String? = ""
)

data class ModuleListState(
    val modules: List<ModuleItemState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredModules: List<ModuleItemState> = emptyList()
)
