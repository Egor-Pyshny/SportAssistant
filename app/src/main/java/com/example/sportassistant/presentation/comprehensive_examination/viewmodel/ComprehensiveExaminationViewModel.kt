package com.example.sportassistant.presentation.comprehensive_examination.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.ComprehensiveExaminationRepository
import com.example.sportassistant.data.schemas.comprehensive_examination.requests.ComprehensiveExaminationCreateRequest
import com.example.sportassistant.domain.model.ComprehensiveExamination
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.presentation.comprehensive_examination.domain.ComprehensiveExaminationUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class ComprehensiveExaminationViewModel(
    private val comprehensiveExaminationRepository: ComprehensiveExaminationRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<ComprehensiveExaminationUiState>(ComprehensiveExaminationUiState())
    val uiState: StateFlow<ComprehensiveExaminationUiState> = _uiState.asStateFlow()

    private val _comprehensiveExaminationAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val comprehensiveExaminationAddResponse = _comprehensiveExaminationAddResponse

    private val _getComprehensiveExaminationResponse = MutableLiveData<ApiResponse<List<ComprehensiveExamination>?>>()
    val getComprehensiveExaminationResponse = _getComprehensiveExaminationResponse

    private val _getComprehensiveExaminationInfoResponse = MutableLiveData<ApiResponse<ComprehensiveExamination?>>()
    val getComprehensiveExaminationInfoResponse = _getComprehensiveExaminationInfoResponse

    private val _updateComprehensiveExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateComprehensiveExaminationResponse = _updateComprehensiveExaminationResponse

    private val _deleteComprehensiveExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteComprehensiveExaminationResponse = _deleteComprehensiveExaminationResponse

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    fun deleteComprehensiveExamination(paramsId: UUID) = baseRequest(
        _deleteComprehensiveExaminationResponse
    ) {
        comprehensiveExaminationRepository.deleteComprehensiveExamination(
            id = paramsId,
        )
    }

    fun clearCreateResponse() {
        _comprehensiveExaminationAddResponse.postValue(null)
    }

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateComprehensiveExaminationResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteComprehensiveExaminationResponse.postValue(null)
    }

    fun updateComprehensiveExamination(data: ComprehensiveExaminationCreateRequest, paramsId: UUID) = baseRequest(
        _updateComprehensiveExaminationResponse
    ) {
        comprehensiveExaminationRepository.updateComprehensiveExamination(
            data = data,
            id = paramsId,
        )
    }

    fun addComprehensiveExamination(data: ComprehensiveExaminationCreateRequest) = baseRequest(
        _comprehensiveExaminationAddResponse
    ) {
        comprehensiveExaminationRepository.createComprehensiveExamination(
            data = data
        )
    }

    fun getComprehensiveExaminationInfo(id: UUID) = baseRequest(
        _getComprehensiveExaminationInfoResponse
    ) {
        comprehensiveExaminationRepository.getComprehensiveExaminationInfo(
            id = id
        )
    }

    fun getComprehensiveExaminations() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getComprehensiveExaminationResponse
            ) {
                comprehensiveExaminationRepository.getComprehensiveExaminations()
            }
        }
    }

    fun setSelectedComprehensiveExamination(params: ComprehensiveExamination) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedComprehensiveExamination = params
            )
        }
    }

    fun setShouldRefetch(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldRefetch = flag
            )
        }
    }
}