package com.example.sportassistant.presentation.calendar.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.repository.CalendarRepository
import com.example.sportassistant.data.schemas.calendar.requests.GetCalendarMonthData
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.domain.model.CalendarMonthData
import com.example.sportassistant.presentation.calendar.domain.CalendarUiState
import com.example.sportassistant.presentation.competition_add.domain.CompetitionUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class CalendarViewModel(
    private val calendarRepository: CalendarRepository
): BaseViewModel() {
    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private val _getMonthDataResponse = MutableLiveData<ApiResponse<CalendarMonthData?>?>()
    val getMonthDataResponse = _getMonthDataResponse

    fun getMonthData(data: GetCalendarMonthData) = baseRequest(
        _getMonthDataResponse
    ) {
        calendarRepository.getMonthData(data)
    }

    fun setSelectedDate(date: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDay = date
            )
        }
    }
}