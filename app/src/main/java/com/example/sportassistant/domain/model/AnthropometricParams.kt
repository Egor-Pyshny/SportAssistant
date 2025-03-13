package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class AnthropometricParams(
    val id: UUID,
    val date: LocalDate,
    val weight: Float,
    val height: Float,
    @SerializedName("chest_circumference")
    val chestCircumference: Float,
)
