package com.example.sportassistant.presentation.comprehensive_examination_add.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.ComprehensiveExaminationRepository
import com.example.sportassistant.data.schemas.comprehensive_examination.requests.ComprehensiveExaminationCreateRequest
import com.example.sportassistant.presentation.comprehensive_examination_add.domain.ComprehensiveExaminationUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class ComprehensiveExaminationAddViewModel(
    private val comprehensiveExaminationRepository: ComprehensiveExaminationRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<ComprehensiveExaminationUiState>(ComprehensiveExaminationUiState())
    val uiState: StateFlow<ComprehensiveExaminationUiState> = _uiState.asStateFlow()

    private val _comprehensiveExaminationAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val comprehensiveExaminationAddResponse = _comprehensiveExaminationAddResponse

    fun addComprehensiveExamination(data: ComprehensiveExaminationCreateRequest) = baseRequest(
        _comprehensiveExaminationAddResponse
    ) {
        comprehensiveExaminationRepository.createComprehensiveExamination(
            data = data
        )
    }

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