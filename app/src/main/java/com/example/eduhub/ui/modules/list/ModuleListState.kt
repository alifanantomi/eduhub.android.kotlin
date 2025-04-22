package com.example.eduhub.ui.modules.list

data class ModuleItemState(
    val title: String = "",
    val image: String = "",
    val summary: String = "",
    val content: String = ""
)

data class ModuleListState(
    val modules: List<ModuleItemState> = emptyList<ModuleItemState>(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredModules: List<ModuleItemState> = emptyList<ModuleItemState>()
)
