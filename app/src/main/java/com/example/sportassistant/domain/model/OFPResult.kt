package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class OFPResult(
    val id: UUID,
    @SerializedName("ofp_category_id")
    val categoryId: UUID,
    val date: LocalDate,
    val result: Float,
    val goals: String,
    val notes: String,
)
