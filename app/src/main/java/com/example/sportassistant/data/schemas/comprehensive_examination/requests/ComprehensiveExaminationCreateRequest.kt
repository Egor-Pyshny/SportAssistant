package com.example.sportassistant.data.schemas.comprehensive_examination.requests

import java.time.LocalDate

data class ComprehensiveExaminationCreateRequest(
    val date: LocalDate,
    val institution: String,
    val specialists: String,
    val methods: String,
    val recommendations: String,
)
