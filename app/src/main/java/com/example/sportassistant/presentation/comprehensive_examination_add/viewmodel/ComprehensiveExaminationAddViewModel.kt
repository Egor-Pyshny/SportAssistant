package com.example.sportassistant.presentation.comprehensive_examination_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.comprehensive_examination_add.domain.ComprehensiveExaminationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class ComprehensiveExaminationAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ComprehensiveExaminationUiState>(ComprehensiveExaminationUiState())
    val uiState: StateFlow<ComprehensiveExaminationUiState> = _uiState.asStateFlow()

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

    fun setSpecialists(specialists: String) {
        _uiState.update { currentState ->
            currentState.copy(
                specialists = specialists
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