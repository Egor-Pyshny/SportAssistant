package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.SFPResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface SFPResultsService {
    @GET("sfp/categories")
    suspend fun getSFPCategories(): Response<List<CategoryModel>>

    @GET("sfp/")
    suspend fun getSFPResults(): Response<List<SFPResult>>

    @POST("sfp/create")
    suspend fun createSFPResult(
        @Body body: SFPResultsCreateRequest,
    ): Response<Void>

    @DELETE("sfp/delete/{sfp_id}")
    suspend fun deleteSFPResult(
        @Path("sfp_id") sfpId: UUID,
    ): Response<Void>

    @POST("sfp/update/{sfp_id}")
    suspend fun updateSFPResult(
        @Body body: SFPResultsCreateRequest,
        @Path("sfp_id") sfpId: UUID,
    ): Response<Void>

    @GET("sfp/{sfp_id}")
    suspend fun getSFPResultInfo(
        @Path("sfp_id") sfpId: UUID,
    ): Response<SFPResult>
}