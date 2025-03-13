package com.example.sportassistant.data.schemas.sfp_results.requests

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class SFPResultsCreateRequest(
    @SerializedName("sfp_category_id")
    val sfpCategoryId: UUID,
    val date: LocalDate,
    val result: Float,
    val goals: String,
    val notes: String,
)