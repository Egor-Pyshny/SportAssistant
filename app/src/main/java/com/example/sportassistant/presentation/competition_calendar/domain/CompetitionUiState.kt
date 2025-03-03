package com.example.sportassistant.presentation.competition_calendar.domain

import com.example.sportassistant.domain.model.Competition
import java.time.LocalDate

data class CompetitionUiState(
    val selectedCompetition: Competition? = null,
    val selectedDay: LocalDate? = null,

    var shouldUpdateCurrent: Boolean = true,
    var shouldUpdateNext: Boolean = true,
    var lastFetched: LocalDate? = null
)
