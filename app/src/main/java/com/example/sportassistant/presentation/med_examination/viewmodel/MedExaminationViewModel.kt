package com.example.sportassistant.presentation.med_examination.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.MedExaminationRepository
import com.example.sportassistant.domain.model.MedExamination
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.util.UUID

class MedExaminationViewModel(
    private val medExaminationRepository: MedExaminationRepository,
): BaseViewModel() {
    private val _getMedExaminationResponse = MutableLiveData<ApiResponse<List<MedExamination>?>>()
    val getMedExaminationResponse = _getMedExaminationResponse

    private val _deleteMedExaminationResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteMedExaminationResponse = _deleteMedExaminationResponse

    fun deleteMedExamination(paramsId: UUID) = baseRequest(
        _deleteMedExaminationResponse
    ) {
        medExaminationRepository.deleteMedExamination(
            id = paramsId,
        )
    }

    fun clearDeleteResponse() {
        _deleteMedExaminationResponse.postValue(null)
    }

    fun getMedExaminations() {
        baseRequest(
            _getMedExaminationResponse
        ) {
            medExaminationRepository.getMedExaminations()
        }
    }
}