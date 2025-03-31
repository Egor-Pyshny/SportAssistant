package com.example.sportassistant.domain.model

import java.time.LocalDate
import java.util.UUID

data class CalendarMonthData(
    val competition: Competition?,
    val camp: TrainingCamp?,

    val dayNotes: Map<LocalDate, Note>,
    val eventDays: Map<LocalDate, List<EventData>>,
)

data class EventData(
    val name: String,
    val id: UUID,
    val type: EventType,
    val dates: List<LocalDate> = listOf(),
)

enum class EventType {
    COMPETITION, CAMP, OFP, SFP, MED, COMPREHENSIVE,
}
