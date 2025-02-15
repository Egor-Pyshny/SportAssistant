package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow

class CoachRepository(
    private val coachService: CoachApiService
) {
    fun getCoaches() = apiRequestFlow {
        coachService.getCoaches()
    }
}