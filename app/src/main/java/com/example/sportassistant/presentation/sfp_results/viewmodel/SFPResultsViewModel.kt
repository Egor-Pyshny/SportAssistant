package com.example.sportassistant.presentation.sfp_results.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.SFPResultsRepository
import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.domain.model.SFPResult
import com.example.sportassistant.presentation.sfp_results.domain.SFPResultsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class SFPResultsViewModel(
    private val sfpRepository: SFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<SFPResultsUiState>(SFPResultsUiState())
    val uiState: StateFlow<SFPResultsUiState> = _uiState.asStateFlow()

    private val _sfpAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val sfpAddResponse = _sfpAddResponse

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    private val _getSFPResultsResponse = MutableLiveData<ApiResponse<List<SFPResult>?>>()
    val getSFPResultsResponse = _getSFPResultsResponse

    private val _getSFPResultInfoResponse = MutableLiveData<ApiResponse<SFPResult?>>()
    val getSFPResultInfoResponse = _getSFPResultInfoResponse

    private val _updateSFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateSFPResultResponse = _updateSFPResultResponse

    private val _deleteSFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteSFPResultResponse = _deleteSFPResultResponse

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    fun deleteSFPResult(sfpId: UUID) = baseRequest(
        _deleteSFPResultResponse
    ) {
        sfpRepository.deleteSFPResult(
            id = sfpId,
        )
    }

    fun clearCreateResponse() {
        _sfpAddResponse.postValue(null)
    }

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateSFPResultResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteSFPResultResponse.postValue(null)
    }

    fun updateSFPResult(data: SFPResultsCreateRequest, sfpId: UUID) = baseRequest(
        _updateSFPResultResponse
    ) {
        sfpRepository.updateSFPResult(
            data = data,
            id = sfpId,
        )
    }

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        categoryId: UUID
    ) = baseRequest(
        _getGraphicDataResponse
    ) {
        sfpRepository.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            categoryId = categoryId,
        )
    }

    fun addSFPResult(data: SFPResultsCreateRequest) = baseRequest(
        _sfpAddResponse
    ) {
        sfpRepository.createSFPResult(
            data = data
        )
    }

    fun getSFPResultInfo(id: UUID) = baseRequest(
        _getSFPResultInfoResponse
    ) {
        sfpRepository.getSFPResultInfo(
            id = id
        )
    }

    fun getCategories() = baseRequest(
        _getCategoriesResponse
    ) {
        sfpRepository.getCategories()
    }

    fun getResults() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getSFPResultsResponse
            ) {
                sfpRepository.getSFPResults()
            }
        }
    }

    fun setSelectedSFP(sfp: SFPResult) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSFP = sfp
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