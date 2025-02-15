package com.example.sportassistant.domain.model

import java.time.ZonedDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val surname: String,
    val sportType: String,
    val qualification: String,
    val address: String,
    val phoneNumber: String,
    val sex: String,
    val coachId: UUID,
    val email: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)
