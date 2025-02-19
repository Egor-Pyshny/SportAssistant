package com.example.sportassistant.presentation.competition_calendar.viewmodel

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.sportassistant.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CompetitionTitleViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<String>("")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    fun setTitle(title: String) {
        _uiState.update {title}
    }
}