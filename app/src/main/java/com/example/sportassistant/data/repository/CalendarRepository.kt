package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.calendar.requests.GetCalendarMonthData
import com.example.sportassistant.domain.interfaces.services.CalendarApiService
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow

class CalendarRepository(
    private val calendarService: CalendarApiService
) {
    fun getMonthData(data: GetCalendarMonthData) = apiRequestFlow {
        calendarService.getCalendarData(
            day = data.day,
            month = data.month,
            year = data.year,
        )
    }
}