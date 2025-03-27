package com.example.sportassistant.presentation.sfp_results.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.SFPResultsRepository
import com.example.sportassistant.domain.model.SFPResult
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
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