package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.domain.model.AnthropometricParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface AnthropometricParamsService {
    @GET("anthropometric_params/")
    suspend fun getAnthropometricParams(): Response<List<AnthropometricParams>>

    @POST("anthropometric_params/create")
    suspend fun createAnthropometricParams(
        @Body body: AnthropometricParamsCreateRequest,
    ): Response<Void>

    @DELETE("anthropometric_params/delete/{params_id}")
    suspend fun deleteAnthropometricParams(
        @Path("params_id") paramsId: UUID,
    ): Response<Void>

    @POST("anthropometric_params/update/{params_id}")
    suspend fun updateAnthropometricParams(
        @Body body: AnthropometricParamsCreateRequest,
        @Path("params_id") paramsId: UUID,
    ): Response<Void>

    @GET("anthropometric_params/{params_id}")
    suspend fun getAnthropometricParamsInfo(
        @Path("params_id") paramsId: UUID,
    ): Response<AnthropometricParams>
}