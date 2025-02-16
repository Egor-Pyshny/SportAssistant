package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("user/get_me")
    suspend fun getMe(): Response<User>

    @POST("user/check_email")
    suspend fun checkEmail(
        @Body body: CheckEmailRequest,
    ): Response<Void>
}