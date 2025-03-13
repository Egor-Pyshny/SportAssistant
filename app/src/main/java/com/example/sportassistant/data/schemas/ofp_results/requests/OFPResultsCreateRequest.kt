package com.example.sportassistant.data.schemas.ofp_results.requests

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class OFPResultsCreateRequest(
    @SerializedName("ofp_category_id")
    val ofpCategoryId: UUID,
    val date: LocalDate,
    val result: Float,
    val goals: String,
    val notes: String,
)