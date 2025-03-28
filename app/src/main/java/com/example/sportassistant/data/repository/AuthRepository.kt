package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.auth.requests.ChangePasswordRequest
import com.example.sportassistant.data.schemas.auth.requests.ForgotPasswordRequest
import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.RegistrationRequest
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
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

    fun resendVerificationCode(data: ResendCodeRequest) = apiRequestFlow  {
        authService.resendVerificationCode(data)
    }

    fun getPasswordCode(data: ForgotPasswordRequest) = apiRequestFlow  {
        authService.getPasswordCode(data)
    }

    fun checkPasswordCode(data: VerifyEmailRequest) = apiRequestFlow  {
        authService.checkPasswordCode(data)
    }

    fun changePassword(data: ChangePasswordRequest) = apiRequestFlow  {
        authService.changePassword(data)
    }

    fun resendPasswordCode(data: ResendCodeRequest) = apiRequestFlow  {
        authService.resendPasswordCode(data)
    }
}