package com.example.sportassistant.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CoachRepository
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel

class CoachViewModel(
    private val coachRepository: CoachRepository,
): BaseViewModel() {
    private val _coachesResponse = MutableLiveData<ApiResponse<List<Coach>?>?>()
    val coachesResponse = _coachesResponse

    init {
        getCoaches()
    }

    fun resetCheckEmailResponse() {
        _coachesResponse.value = null
    }

    fun getCoaches() = baseRequest(
        _coachesResponse
    ) {
        coachRepository.getCoaches()
    }
}