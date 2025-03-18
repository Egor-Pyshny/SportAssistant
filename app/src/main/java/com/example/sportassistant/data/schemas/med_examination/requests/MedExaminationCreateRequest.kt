package com.example.sportassistant.data.schemas.med_examination.requests

import java.time.LocalDate
import java.util.UUID

data class MedExaminationCreateRequest(
    val date: LocalDate,
    val institution: String,
    val methods: String,
    val recommendations: String,
)
