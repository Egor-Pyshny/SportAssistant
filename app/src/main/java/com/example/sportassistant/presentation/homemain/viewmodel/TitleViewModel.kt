package com.example.sportassistant.presentation.homemain.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TitleViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<String>("")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    fun setTitle(title: String) {
        _uiState.update {title}
    }
}