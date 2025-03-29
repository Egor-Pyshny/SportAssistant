package com.example.sportassistant.domain.model

import java.time.LocalDate
import java.util.UUID

data class CalendarMonthData(
    val competition: Competition?,
    val camp: TrainingCamp?,
    val note: Note?,

    val eventDays: Map<LocalDate, List<EventData>>,
)

data class EventData(
    val name: String,
    val id: UUID,
    val type: EventType,
)

enum class EventType {
    COMPETITION, CAMP, OFP, SFP, MED, COMPREHENSIVE,
}
