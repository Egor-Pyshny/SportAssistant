package com.example.sportassistant.data.schemas.auth.requests

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class RegistrationRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    @SerializedName("device_id")
    val deviceId: UUID,
)
