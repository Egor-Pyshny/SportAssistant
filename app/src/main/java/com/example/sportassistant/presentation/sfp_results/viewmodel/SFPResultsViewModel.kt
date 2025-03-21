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

    private val _getSFPResultsResponse = MutableLiveData<ApiResponse<List<SFPResult>?>>()
    val getSFPResultsResponse = _getSFPResultsResponse

    private val _deleteSFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteSFPResultResponse = _deleteSFPResultResponse

    fun deleteSFPResult(sfpId: UUID) = baseRequest(
        _deleteSFPResultResponse
    ) {
        sfpRepository.deleteSFPResult(
            id = sfpId,
        )
    }

    fun clearDeleteResponse() {
        _deleteSFPResultResponse.postValue(null)
    }

    fun getResults() {
        baseRequest(
            _getSFPResultsResponse
        ) {
            sfpRepository.getSFPResults()
        }
    }
}