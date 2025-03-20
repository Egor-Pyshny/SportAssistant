package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.domain.model.SFPResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.util.UUID

interface SFPResultsApiService {
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

    @GET("sfp/get/{sfp_id}")
    suspend fun getSFPResultInfo(
        @Path("sfp_id") sfpId: UUID,
    ): Response<SFPResult>

    @GET("sfp/graphic_data")
    suspend fun getGraphicData(
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate,
        @Query("category_id") categoryId: UUID,
    ): Response<List<GraphicPoint>>
}