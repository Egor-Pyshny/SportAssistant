package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val surname: String,
    @SerializedName("sport_type")
    val sportType: String,
    val qualification: String,
    val address: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val sex: String,
    val coach: Coach,
    val email: String,
    @SerializedName("created_at")
    val createdAt: ZonedDateTime,
    @SerializedName("updated_at")
    val updatedAt: ZonedDateTime,
)
