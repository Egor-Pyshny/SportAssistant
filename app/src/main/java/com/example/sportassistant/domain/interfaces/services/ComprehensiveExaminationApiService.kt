package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.comprehensive_examination.requests.ComprehensiveExaminationCreateRequest
import com.example.sportassistant.domain.model.ComprehensiveExamination
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ComprehensiveExaminationApiService {
    @GET("comprehensive_examination/")
    suspend fun getComprehensiveExaminations(): Response<List<ComprehensiveExamination>>

    @POST("comprehensive_examination/create")
    suspend fun createComprehensiveExamination(
        @Body body: ComprehensiveExaminationCreateRequest,
    ): Response<Void>

    @DELETE("comprehensive_examination/delete/{exam_id}")
    suspend fun deleteComprehensiveExamination(
        @Path("exam_id") examId: UUID,
    ): Response<Void>

    @POST("comprehensive_examination/update/{exam_id}")
    suspend fun updateComprehensiveExamination(
        @Body body: ComprehensiveExaminationCreateRequest,
        @Path("exam_id") examId: UUID,
    ): Response<Void>

    @GET("comprehensive_examination/get/{exam_id}")
    suspend fun getComprehensiveExaminationInfo(
        @Path("exam_id") examId: UUID,
    ): Response<ComprehensiveExamination>
}