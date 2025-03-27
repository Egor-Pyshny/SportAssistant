package com.example.sportassistant.data.schemas.auth.requests

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SetProfileDataRequest(
    @SerializedName("sport_type")
    val sportType: String,
    val qualification: String,
    val address: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val sex: String,
    @SerializedName("coach_id")
    val coachId: UUID,
)
