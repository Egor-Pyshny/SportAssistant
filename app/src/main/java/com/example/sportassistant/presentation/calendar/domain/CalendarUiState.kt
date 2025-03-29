package com.example.sportassistant.presentation.calendar.domain

import java.time.LocalDate

data class CalendarUiState(
    val selectedDay: LocalDate? = null,
)