package com.example.sportassistant.presentation.med_examination_add.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.MedExaminationRepository
import com.example.sportassistant.data.schemas.med_examination.requests.MedExaminationCreateRequest
import com.example.sportassistant.presentation.med_examination_add.domain.MedExaminationUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class MedExaminationAddViewModel(
    private val medExaminationRepository: MedExaminationRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<MedExaminationUiState>(
        MedExaminationUiState()
    )
    val uiState: StateFlow<MedExaminationUiState> = _uiState.asStateFlow()

    private val _medExaminationAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val medExaminationAddResponse = _medExaminationAddResponse

    fun addMedExamination(data: MedExaminationCreateRequest) = baseRequest(
        _medExaminationAddResponse
    ) {
        medExaminationRepository.createMedExamination(
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