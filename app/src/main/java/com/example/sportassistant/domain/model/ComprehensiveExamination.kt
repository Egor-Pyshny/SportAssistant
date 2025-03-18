package com.example.sportassistant.domain.model

import java.time.LocalDate
import java.util.UUID

data class ComprehensiveExamination(
    val id: UUID,
    val date: LocalDate,
    val institution: String,
    val specialists: String,
    val methods: String,
    val recommendations: String,
)
