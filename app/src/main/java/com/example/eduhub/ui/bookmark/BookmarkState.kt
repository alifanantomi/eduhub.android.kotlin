package com.example.eduhub.ui.bookmark

import com.example.eduhub.ui.modules.list.ModuleItemState

data class BookmarkState(
    val bookmarks: List<ModuleItemState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
