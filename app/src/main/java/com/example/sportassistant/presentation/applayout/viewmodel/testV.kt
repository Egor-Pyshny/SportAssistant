package com.example.sportassistant.presentation.applayout.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class testV: ViewModel() {
    private val _uiState = MutableStateFlow<TestS>(TestS())
    val uiState: StateFlow<TestS> = _uiState.asStateFlow()

    fun setName(userName: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                b = userName,
            )
        }
    }
}

data class TestS(
    val b: Boolean = false,
)