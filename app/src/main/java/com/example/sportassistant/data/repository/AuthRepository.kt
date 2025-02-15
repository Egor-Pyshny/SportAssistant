package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.RegistrationRequest
import com.example.sportassistant.data.schemas.auth.requests.VerifyEmailRequest
import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.apiRequestFlow

class AuthRepository(
    private val authService: AuthApiService
) {
    fun login(data: LoginRequest) = apiRequestFlow {
        authService.login(data)
    }

    fun registration(data: RegistrationRequest) = apiRequestFlow {
        authService.registration(data)
    }

    fun verifyEmail(data: VerifyEmailRequest) = apiRequestFlow {
        authService.verifyEmail(data)
    }
}