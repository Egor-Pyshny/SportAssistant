package com.example.sportassistant.data.schemas.competition.requests

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CreateCompetitionRequest(
    @SerializedName("start_date")
    val startDate: LocalDate,
    @SerializedName("end_date")
    val endDate: LocalDate,
    val location: String,
    val notes: String,
    val name: String,
)
