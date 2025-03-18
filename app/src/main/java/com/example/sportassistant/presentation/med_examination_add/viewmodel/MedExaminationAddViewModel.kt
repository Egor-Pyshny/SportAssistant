package com.example.sportassistant.presentation.med_examination_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.med_examination_add.domain.MedExaminationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class MedExaminationAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MedExaminationUiState>(
        MedExaminationUiState()
    )
    val uiState: StateFlow<MedExaminationUiState> = _uiState.asStateFlow()

    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setInstitution(institution: String) {
        _uiState.update { currentState ->
            currentState.copy(
                institution = institution
            )
        }
    }

    fun setMethods(methods: String) {
        _uiState.update { currentState ->
            currentState.copy(
                methods = methods
            )
        }
    }

    fun setRecommendations(recommendations: String) {
        _uiState.update { currentState ->
            currentState.copy(
                recommendations = recommendations
            )
        }
    }
}