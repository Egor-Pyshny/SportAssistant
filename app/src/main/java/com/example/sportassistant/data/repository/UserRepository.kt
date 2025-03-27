package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.domain.interfaces.services.UserApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow

class UserRepository(
    private val userService: UserApiService
) {
    fun checkEmail(data: CheckEmailRequest) = apiRequestFlow {
        userService.checkEmail(data)
    }

    fun getMe() = apiRequestFlow {
        userService.getMe()
    }

    fun setInfo(data: SetProfileDataRequest) = apiRequestFlow {
        userService.setProfileInfo(data)
    }

    fun isProfileFilled() = apiRequestFlow {
        userService.isProfileDataFilled()
    }
}