package com.example.eduhub.ui.modules.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ModuleItemViewModel: ViewModel() {
    var state by mutableStateOf(ModuleListState())
        private set

    private var _navigateToDetail = mutableStateOf(false)
    val navigateToDetail: Boolean get() = _navigateToDetail.value

    fun onDetailClick() {
        viewModelScope.launch {
            _navigateToDetail.value = true
        }
    }

    fun onNavigatedToDetail() {
        _navigateToDetail.value = false
    }
}