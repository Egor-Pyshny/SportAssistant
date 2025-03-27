package com.example.sportassistant.presentation.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.domain.model.User
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileInfoViewModel(
    private val userRepository: UserRepository
): BaseViewModel() {
    private val _getMeResponse = MutableLiveData<ApiResponse<User?>?>()
    val getMeResponse = _getMeResponse

    private val _shouldRefetch = MutableStateFlow<Boolean>(true)
    val shouldRefetch: StateFlow<Boolean> = _shouldRefetch.asStateFlow()

    fun fetchData() = baseRequest(
        _getMeResponse
    ) {
        userRepository.getMe()
    }

    fun setRefetch(shouldRefetch: Boolean) {
        _shouldRefetch.update {
            shouldRefetch
        }
    }
}