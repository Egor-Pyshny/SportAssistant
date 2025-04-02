package com.example.sportassistant.presentation.train_diary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.train_diary.domain.Meal
import com.example.sportassistant.presentation.train_diary.domain.Train
import com.example.sportassistant.presentation.train_diary.domain.TrainDiaryUiState
import com.example.sportassistant.presentation.train_diary.ui.PreparationType
import com.example.sportassistant.presentation.trainig_camps_add.domain.TrainingCampUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class TrainDiaryViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<TrainDiaryUiState>(TrainDiaryUiState())
    val uiState: StateFlow<TrainDiaryUiState> = _uiState.asStateFlow()

    fun getMealById(id: Int): Meal {
        return _uiState.value.dayMeals.find { it.id == id }!!
    }

    fun getMealId(): Int {
        return _uiState.value.dayMeals.size+1
    }

    fun setDayActivity(activity: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dayActivity = activity
            )
        }
    }

    fun setEveningHealth(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                eveningHealth = value
            )
        }
    }

    fun setSleepQuality(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                sleepQuality = value
            )
        }
    }

    fun setStateAfterSleep(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                stateAfterSleep = value
            )
        }
    }
    
    fun setHeartRateLaying(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateLaying = value
            )
        }
    }

    fun setHeartRateStaying(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateStaying = value
            )
        }
    }

    fun setDayTired(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dayTired = value
            )
        }
    }

    fun setGoToBedTime(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                goToBedTime = value
            )
        }
    }

    fun setWakeUpTime(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                wakeUpTime = value
            )
        }
    }

    fun setSleepDuration(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                sleepDuration = value
            )
        }
    }

    fun setDayWater(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dayWater = value
            )
        }
    }

    fun setSportFood(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                daySportFood = value
            )
        }
    }

    fun addMeal(value: Meal) {
        _uiState.update { currentState ->
            val meals = currentState.dayMeals + value
            currentState.copy(
                dayMeals = meals
            )
        }
    }

    fun updateMeal(value: Meal) {
        _uiState.update { currentState ->
            val meals = currentState.dayMeals.filterNot { it.id == value.id }
            val newMeals = meals + value
            currentState.copy(
                dayMeals = newMeals
            )
        }
    }

    fun getTrainByType(type: PreparationType): Train {
        return when (type) {
            PreparationType.GENERAL -> _uiState.value.generalTrain
            PreparationType.SPEC -> _uiState.value.generalTrain
            PreparationType.PRED_COMP -> _uiState.value.generalTrain
            PreparationType.COMP -> _uiState.value.generalTrain
            PreparationType.TRANSITIONAL -> _uiState.value.generalTrain
        }
    }
}