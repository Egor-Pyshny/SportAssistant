package com.example.sportassistant.data.schemas.training_camps.requests

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CreateTrainingCampRequest(
    @SerializedName("start_date")
    val startDate: LocalDate,
    @SerializedName("end_date")
    val endDate: LocalDate,
    val location: String,
    val notes: String,
    val goals: String,
)
