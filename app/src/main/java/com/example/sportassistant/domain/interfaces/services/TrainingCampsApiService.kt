package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.model.TrainingCamp
import com.example.sportassistant.domain.model.TrainingCampDay
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.util.UUID

interface TrainingCampsApiService {
    @GET("camps")
    suspend fun getTrainingCamps(
        @Query("status") status: String,
        @Query("current_date") date: LocalDate,
    ): Response<List<TrainingCamp>>

    @GET("camps/{camp_id}/days/{day}")
    suspend fun getTrainingCampDay(
        @Path("camp_id") campId: UUID,
        @Path("day") day: LocalDate,
    ): Response<TrainingCampDay>

    @POST("camps/update/{camp_id}")
    suspend fun updateTrainingCamps(
        @Path("camp_id") campId: UUID,
        @Body body: CreateTrainingCampRequest,
    ): Response<TrainingCamp>

    @POST("camps/update_day/{camp_id}")
    suspend fun updateTrainingCampDay(
        @Path("camp_id") campId: UUID,
        @Body body: TrainingCampDayUpdateRequest,
    ): Response<TrainingCampDay>

    @GET("camps/{camp_id}")
    suspend fun getTrainingCamp(
        @Path("camp_id") campId: UUID,
    ): Response<TrainingCamp>

    @DELETE("camps/delete/{camp_id}")
    suspend fun deleteTrainingCamp(
        @Path("camp_id") campId: UUID,
    ): Response<Void>

    @POST("camps/create")
    suspend fun createTrainingCamp(
        @Body body: CreateTrainingCampRequest,
    ): Response<Void>
}