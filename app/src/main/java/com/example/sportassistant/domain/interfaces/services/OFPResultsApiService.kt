package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.domain.model.OFPResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.util.UUID

interface OFPResultsApiService {
    @GET("ofp/categories")
    suspend fun getOFPCategories(): Response<List<CategoryModel>>

    @GET("ofp/")
    suspend fun getOFPResults(): Response<List<OFPResult>>

    @POST("ofp/create")
    suspend fun createOFPResult(
        @Body body: OFPResultsCreateRequest,
    ): Response<Void>

    @DELETE("ofp/delete/{ofp_id}")
    suspend fun deleteOFPResult(
        @Path("ofp_id") ofpId: UUID,
    ): Response<Void>

    @POST("ofp/update/{ofp_id}")
    suspend fun updateOFPResult(
        @Body body: OFPResultsCreateRequest,
        @Path("ofp_id") ofpId: UUID,
    ): Response<Void>

    @GET("ofp/get/{ofp_id}")
    suspend fun getOFPResultInfo(
        @Path("ofp_id") ofpId: UUID,
    ): Response<OFPResult>

    @GET("ofp/graphic_data")
    suspend fun getGraphicData(
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate,
        @Query("category_id") categoryId: UUID,
    ): Response<List<GraphicPoint>>
}