package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface CoachApiService {
    @GET("coaches")
    suspend fun getCoaches(): Response<List<Coach>>

    @GET("coaches/{coach_id}")
    suspend fun getCoach(
        @Path("coach_id") coachId: UUID,
    ): Response<Coach>
}