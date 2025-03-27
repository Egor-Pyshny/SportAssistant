package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("user/get_me")
    suspend fun getMe(): Response<User>

    @GET("user/is_profile_filled")
    suspend fun isProfileDataFilled(): Response<Boolean>

    @POST("user/check_email")
    suspend fun checkEmail(
        @Body body: CheckEmailRequest,
    ): Response<Void>

    @POST("user/set_profile_info")
    suspend fun setProfileInfo(
        @Body body: SetProfileDataRequest,
    ): Response<Void>

}