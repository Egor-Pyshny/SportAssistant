package com.example.sportassistant.presentation.train_diary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.train_diary.domain.Meal
import com.example.sportassistant.presentation.train_diary.domain.MealUiState
import com.example.sportassistant.presentation.train_diary.domain.TrainDiaryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class MealViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MealUiState>(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    fun setAppetit(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                appetit = value
            )
        }
    }

    fun setMealTime(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                mealTime = value
            )
        }
    }

    fun setNote(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                note = value
            )
        }
    }

    fun setProtein(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                protein = value
            )
        }
    }

    fun setFats(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                fats = value
            )
        }
    }

    fun setCarbs(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                carbs = value
            )
        }
    }
}