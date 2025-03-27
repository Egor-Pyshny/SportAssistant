package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.RegistrationRequest
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.data.schemas.auth.requests.VerifyEmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest,
    ): Response<Void>

    @POST("auth/registration")
    suspend fun registration(
        @Body body: RegistrationRequest,
    ): Response<Void>

    @POST("auth/verify_email")
    suspend fun verifyEmail(
        @Body body: VerifyEmailRequest,
    ): Response<Void>

    @POST("auth/resend_verification_code")
    suspend fun resendVerificationCode(
        @Body body: ResendCodeRequest,
    ): Response<Void>
}