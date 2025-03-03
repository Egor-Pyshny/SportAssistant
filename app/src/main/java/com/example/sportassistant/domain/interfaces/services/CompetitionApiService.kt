package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import retrofit2.Response
import retrofit2.http.Body
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

    @GET("competition/{competition_id}")
    suspend fun getCompetition(
        @Path("competition_id") competitionId: UUID,
    ): Response<Competition>

    @POST("competition/create")
    suspend fun createCompetition(
        @Body body: CreateCompetitionRequest,
    ): Response<Void>

    @GET("competition/{competition_id}/days")
    suspend fun getCompetitionDays(
        @Path("competition_id") competitionId: UUID,
    ): Response<List<CompetitionDay>>
}