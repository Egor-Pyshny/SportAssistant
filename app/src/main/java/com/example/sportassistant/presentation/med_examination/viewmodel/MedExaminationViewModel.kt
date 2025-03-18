package com.example.sportassistant.presentation.med_examination.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.MedExaminationRepository
import com.example.sportassistant.data.schemas.med_examination.requests.MedExaminationCreateRequest
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.domain.model.MedExamination
import com.example.sportassistant.presentation.med_examination.domain.MedExaminationUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MedExaminationViewModel(
    private val medExaminationRepository: MedExaminationRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<MedExaminationUiState>(MedExaminationUiState())
    val uiState: StateFlow<MedExaminationUiState> = _uiState.asStateFlow()

    private val _medExaminationAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val medExaminationAddResponse = _medExaminationAddResponse

    private val _getMedExaminationResponse = MutableLiveData<ApiResponse<List<MedExamination>?>>()
    val getMedExaminationResponse = _getMedExaminationResponse

    private val _getMedExaminationInfoResponse = MutableLiveData<ApiResponse<MedExamination?>>()
    val getMedExaminationInfoResponse = _getMedExaminationInfoResponse

    private val _updateMedExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateMedExaminationResponse = _updateMedExaminationResponse

    private val _deleteMedExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteMedExaminationResponse = _deleteMedExaminationResponse

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    fun deleteMedExamination(paramsId: UUID) = baseRequest(
        _deleteMedExaminationResponse
    ) {
        medExaminationRepository.deleteMedExamination(
            id = paramsId,
        )
    }

    fun clearCreateResponse() {
        _medExaminationAddResponse.postValue(null)
    }

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateMedExaminationResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteMedExaminationResponse.postValue(null)
    }

    fun updateMedExamination(data: MedExaminationCreateRequest, paramsId: UUID) = baseRequest(
        _updateMedExaminationResponse
    ) {
        medExaminationRepository.updateMedExamination(
            data = data,
            id = paramsId,
        )
    }

    fun addMedExamination(data: MedExaminationCreateRequest) = baseRequest(
        _medExaminationAddResponse
    ) {
        medExaminationRepository.createMedExamination(
            data = data
        )
    }

    fun getMedExaminationInfo(id: UUID) = baseRequest(
        _getMedExaminationInfoResponse
    ) {
        medExaminationRepository.getMedExaminationInfo(
            id = id
        )
    }

    fun getMedExaminations() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getMedExaminationResponse
            ) {
                medExaminationRepository.getMedExaminations()
            }
        }
    }

    fun setSelectedMedExamination(params: MedExamination) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMedExamination = params
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