package com.example.sportassistant.presentation.train_diary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.train_diary.domain.Train
import com.example.sportassistant.presentation.train_diary.domain.TrainDiaryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class TrainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<Train>(Train())
    val uiState: StateFlow<Train> = _uiState.asStateFlow()

    fun loadTrain(value: Train) {
        _uiState.update {
            value
        }
    }

    fun setWishToTrain(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                wishToTrain = value
            )
        }
    }

    fun setSelfBeing(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selfBeing = value
            )
        }
    }

    fun setHeartRateBeforeWarmUp(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateBeforeWarmUp = value
            )
        }
    }

    fun setHeartRateAfterWarmUp(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateAfterWarmUp = value
            )
        }
    }

    fun setWarmUpDuration(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                warmUpDuration = value
            )
        }
    }

    fun setWarmUpNote(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                warmUpNote = value
            )
        }
    }

    fun setHeartRateBeforeMain(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateBeforeMain = value
            )
        }
    }

    fun setHeartRateAfterMain(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateAfterMain = value
            )
        }
    }

    fun setMainDuration(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                mainDuration = value
            )
        }
    }

    fun setMainNote(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                mainNote = value
            )
        }
    }

    fun setHeartRateBeforeFinish(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateBeforeFinish = value
            )
        }
    }

    fun setHeartRateAfterFinish(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                heartRateAfterFinish = value
            )
        }
    }

    fun setWorkCapacity(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                workCapacity = value
            )
        }
    }

    fun setDegreeOfFatigue(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                degreeOfFatigue = value
            )
        }
    }

    fun setFinishDuration(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                finishDuration = value
            )
        }
    }

    fun setFinishNote(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                finishNote = value
            )
        }
    }

    fun setTrainStart(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                trainStart = value
            )
        }
    }

    fun setTrainEnd(value: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                trainEnd = value
            )
        }
    }
}