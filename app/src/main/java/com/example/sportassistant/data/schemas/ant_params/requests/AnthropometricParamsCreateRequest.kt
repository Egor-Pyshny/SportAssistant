package com.example.sportassistant.data.schemas.ant_params.requests

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class AnthropometricParamsCreateRequest(
    val date: LocalDate,
    val weight: Float,
    val height: Float,
    @SerializedName("chest_circumference")
    val chestCircumference: Float,
)
