package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class Competition(
    val id: UUID,
    @SerializedName("start_date")
    val startDate: LocalDate,
    @SerializedName("end_date")
    val endDate: LocalDate,
    val location: String,
    val notes: String,
    val name: String,
)
