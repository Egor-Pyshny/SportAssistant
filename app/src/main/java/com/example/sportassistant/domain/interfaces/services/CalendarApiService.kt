package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.domain.model.CalendarMonthData
import com.example.sportassistant.domain.model.Coach
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CalendarApiService {
    @GET("calendar")
    suspend fun getCalendarData(
        @Query("day") day: Int?,
        @Query("month") month: Int,
        @Query("year") year: Int,
    ): Response<CalendarMonthData>
}