package com.example.sportassistant.presentation.ofp_results.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.repository.OFPResultsRepository
import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.presentation.competition_calendar.domain.CompetitionUiState
import com.example.sportassistant.presentation.ofp_results.domain.OFPResultsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class OFPResultsViewModel(
    private val ofpRepository: OFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<OFPResultsUiState>(OFPResultsUiState())
    val uiState: StateFlow<OFPResultsUiState> = _uiState.asStateFlow()

    private val _ofpAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val ofpAddResponse = _ofpAddResponse

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    private val _getOFPResultsResponse = MutableLiveData<ApiResponse<List<OFPResult>?>>()
    val getOFPResultsResponse = _getOFPResultsResponse

    private val _getOFPResultInfoResponse = MutableLiveData<ApiResponse<OFPResult?>>()
    val getOFPResultInfoResponse = _getOFPResultInfoResponse

    private val _updateOFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateOFPResultResponse = _updateOFPResultResponse

    private val _deleteOFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteOFPResultResponse = _deleteOFPResultResponse

    fun deleteOFPResult(ofpId: UUID) = baseRequest(
        _deleteOFPResultResponse
    ) {
        ofpRepository.deleteOFPResult(
            id = ofpId,
        )
    }

    fun clearCreateResponse() {
        _ofpAddResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateOFPResultResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteOFPResultResponse.postValue(null)
    }

    fun updateOFPResult(data: OFPResultsCreateRequest, ofpId: UUID) = baseRequest(
        _updateOFPResultResponse
    ) {
        ofpRepository.updateOFPResult(
            data = data,
            id = ofpId,
        )
    }

    fun addOFPResult(data: OFPResultsCreateRequest) = baseRequest(
        _ofpAddResponse
    ) {
        ofpRepository.createOFPResult(
            data = data
        )
    }

    fun getOFPResultInfo(id: UUID) = baseRequest(
        _getOFPResultInfoResponse
    ) {
        ofpRepository.getOFPResultInfo(
            id = id
        )
    }

    fun getCategories() = baseRequest(
        _getCategoriesResponse
    ) {
        ofpRepository.getCategories()
    }

    fun getResults() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getOFPResultsResponse
            ) {
                ofpRepository.getOFPResults()
            }
        }
    }

    fun setSelectedOFP(ofp: OFPResult) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedOFP = ofp
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