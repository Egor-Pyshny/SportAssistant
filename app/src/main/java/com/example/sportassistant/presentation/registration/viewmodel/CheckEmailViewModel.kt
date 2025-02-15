package com.example.sportassistant.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel

class CheckEmailViewModel(
    private val userRepository: UserRepository,
): BaseViewModel() {
    private val _checkEmailResponse = MutableLiveData<ApiResponse<Void?>?>()
    val checkEmailResponse = _checkEmailResponse

    init {
        _checkEmailResponse.value = null
    }

    fun resetCheckEmailResponse() {
        _checkEmailResponse.value = null
    }

    fun checkEmail(data: CheckEmailRequest) = baseRequest(
        _checkEmailResponse
    ) {
        userRepository.checkEmail(data)
    }
}