package com.example.sportassistant.presentation.comprehensive_examination.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.ComprehensiveExaminationRepository
import com.example.sportassistant.domain.model.ComprehensiveExamination
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.util.UUID

class ComprehensiveExaminationViewModel(
    private val comprehensiveExaminationRepository: ComprehensiveExaminationRepository,
): BaseViewModel() {
    private val _getComprehensiveExaminationResponse = MutableLiveData<ApiResponse<List<ComprehensiveExamination>?>>()
    val getComprehensiveExaminationResponse = _getComprehensiveExaminationResponse

    private val _deleteComprehensiveExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteComprehensiveExaminationResponse = _deleteComprehensiveExaminationResponse

    fun deleteComprehensiveExamination(paramsId: UUID) = baseRequest(
        _deleteComprehensiveExaminationResponse
    ) {
        comprehensiveExaminationRepository.deleteComprehensiveExamination(
            id = paramsId,
        )
    }

    fun clearDeleteResponse() {
        _deleteComprehensiveExaminationResponse.postValue(null)
    }

    fun getComprehensiveExaminations() {
        baseRequest(
            _getComprehensiveExaminationResponse
        ) {
            comprehensiveExaminationRepository.getComprehensiveExaminations()
        }
    }
}