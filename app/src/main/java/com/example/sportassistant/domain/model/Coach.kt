package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Coach(
    val id: UUID,
    val fio: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val institution: String
)