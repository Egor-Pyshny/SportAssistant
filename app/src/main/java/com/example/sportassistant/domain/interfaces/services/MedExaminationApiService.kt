package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.med_examination.requests.MedExaminationCreateRequest
import com.example.sportassistant.domain.model.MedExamination
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface MedExaminationApiService {
    @GET("med_examination/")
    suspend fun getMedExaminations(): Response<List<MedExamination>>

    @POST("med_examination/create")
    suspend fun createMedExamination(
        @Body body: MedExaminationCreateRequest,
    ): Response<Void>

    @DELETE("med_examination/delete/{exam_id}")
    suspend fun deleteMedExamination(
        @Path("exam_id") examId: UUID,
    ): Response<Void>

    @POST("med_examination/update/{exam_id}")
    suspend fun updateMedExamination(
        @Body body: MedExaminationCreateRequest,
        @Path("exam_id") examId: UUID,
    ): Response<Void>

    @GET("med_examination/get/{exam_id}")
    suspend fun getMedExaminationInfo(
        @Path("exam_id") examId: UUID,
    ): Response<MedExamination>
}