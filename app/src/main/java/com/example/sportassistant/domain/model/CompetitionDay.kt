package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class CompetitionDay(
    val id: UUID?,
    @SerializedName("competition_start_date")
    var competitionStartDate: LocalDate,
    @SerializedName("competition_end_date")
    val competitionEndDate: LocalDate,
    @SerializedName("competition_location")
    val competitionLocation: String,
    @SerializedName("competition_name")
    val competitionName: String,
    val date: LocalDate,
    val results: String,
    val notes: String,
)
