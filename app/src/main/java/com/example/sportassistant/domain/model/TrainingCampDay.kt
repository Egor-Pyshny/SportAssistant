package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class TrainingCampDay(
    val id: UUID?,
    @SerializedName("camp_start_date")
    var campStartDate: LocalDate,
    @SerializedName("camp_end_date")
    val campEndDate: LocalDate,
    @SerializedName("camp_location")
    val campLocation: String,
    val date: LocalDate,
    val goals: String,
    val notes: String,
)
