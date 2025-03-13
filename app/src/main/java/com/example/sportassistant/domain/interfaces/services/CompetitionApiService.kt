package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.data.schemas.competition_result.requests.CompetitionResultUpdateRequest
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.domain.model.CompetitionResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.util.UUID

interface CompetitionApiService {
    @GET("competition")
    suspend fun getCompetitions(
        @Query("status") status: String,
        @Query("current_date") date: LocalDate,
    ): Response<List<Competition>>

    @GET("competition/{competition_id}/days/{day}")
    suspend fun getCompetitionDay(
        @Path("competition_id") competitionId: UUID,
        @Path("day") day: LocalDate,
    ): Response<CompetitionDay>

    @POST("competition/update/{competition_id}")
    suspend fun updateCompetitions(
        @Path("competition_id") competitionId: UUID,
        @Body body: CreateCompetitionRequest,
    ): Response<Competition>

    @POST("competition/update_day/{competition_id}")
    suspend fun updateCompetitionDay(
        @Path("competition_id") competitionId: UUID,
        @Body body: CompetitionDayUpdateRequest,
    ): Response<CompetitionDay>

    @POST("competition/update_result/{competition_id}")
    suspend fun updateCompetitionResult(
        @Path("competition_id") competitionId: UUID,
        @Body body: CompetitionResultUpdateRequest,
    ): Response<CompetitionResult>

    @GET("competition/{competition_id}")
    suspend fun getCompetition(
        @Path("competition_id") competitionId: UUID,
    ): Response<Competition>

    @GET("competition/result/{competition_id}")
    suspend fun getCompetitionResult(
        @Path("competition_id") competitionId: UUID,
    ): Response<CompetitionResult>

    @DELETE("competition/delete/{competition_id}")
    suspend fun deleteCompetition(
        @Path("competition_id") competitionId: UUID,
    ): Response<Void>

    @POST("competition/create")
    suspend fun createCompetition(
        @Body body: CreateCompetitionRequest,
    ): Response<Void>
}