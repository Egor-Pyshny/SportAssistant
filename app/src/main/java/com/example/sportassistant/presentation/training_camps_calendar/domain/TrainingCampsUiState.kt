package com.example.sportassistant.presentation.training_camps_calendar.domain

import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.TrainingCamp
import java.time.LocalDate

data class TrainingCampsUiState(
    val selectedCamp: TrainingCamp? = null,
    val selectedDay: LocalDate? = null,

    var shouldUpdateCurrent: Boolean = true,
    var shouldUpdateNext: Boolean = true,
    var lastFetched: LocalDate? = null
)