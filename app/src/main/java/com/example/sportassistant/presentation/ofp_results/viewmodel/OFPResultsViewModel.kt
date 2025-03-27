package com.example.sportassistant.presentation.ofp_results.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.OFPResultsRepository
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.util.UUID

class OFPResultsViewModel(
    private val ofpRepository: OFPResultsRepository,
): BaseViewModel() {
    private val _getOFPResultsResponse = MutableLiveData<ApiResponse<List<OFPResult>?>>()
    val getOFPResultsResponse = _getOFPResultsResponse

    private val _deleteOFPResultResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteOFPResultResponse = _deleteOFPResultResponse

    fun deleteOFPResult(ofpId: UUID) = baseRequest(
        _deleteOFPResultResponse
    ) {
        ofpRepository.deleteOFPResult(
            id = ofpId,
        )
    }

    fun clearDeleteResponse() {
        _deleteOFPResultResponse.postValue(null)
    }

    fun getResults() {
        baseRequest(
            _getOFPResultsResponse
        ) {
            ofpRepository.getOFPResults()
        }
    }
}