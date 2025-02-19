package com.example.sportassistant.presentation.competition_calendar.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TabsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<Int>(1)
    val uiState: StateFlow<Int> = _uiState.asStateFlow()

    fun setSelectedTab(tab: Int) {
        _uiState.update {tab}
    }
}
