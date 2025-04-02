package com.example.sportassistant.presentation.train_diary.domain

import java.time.LocalTime

data class TrainDiaryUiState(
    val dayActivity: Int = 5,
    val eveningHealth: Int = 5,
    val dayTired: Int = 5,
    val goToBedTime: LocalTime = LocalTime.now(),

    val sleepQuality: Int = 5,
    val stateAfterSleep: Int = 5,
    val heartRateLaying: Int = 11,
    val heartRateStaying: Int = 11,
    val sleepDuration: LocalTime = LocalTime.of(8,0,0),
    val wakeUpTime: LocalTime = LocalTime.of(9,0,0),

    val dayWater: Int = 1,
    val daySportFood: Int = 1,
    val dayMeals: List<Meal> = listOf(),

    val generalTrain: Train = Train(),
    val specialTrain: Train = Train(),
    val predCompTrain: Train = Train(),
    val compTrain: Train = Train(),
    val transitionalTrain: Train = Train(),
)

data class Meal(
    val id: Int,
    val appetit: Int = 5,
    val mealTime: LocalTime = LocalTime.now(),
    val note: String = "",
    val protein: Int = 1,
    val fats: Int = 1,
    val carbs: Int = 1,
)

data class MealUiState(
    val appetit: Int = 5,
    val mealTime: LocalTime = LocalTime.now(),
    val note: String = "",
    val protein: Int = 1,
    val fats: Int = 1,
    val carbs: Int = 1,
)

data class Train(
    val wishToTrain: Int = 5,
    val selfBeing: Int = 5,
    val trainStart: LocalTime = LocalTime.now(),
    val trainEnd: LocalTime = LocalTime.now().plusHours(1).plusMinutes(30),

    val heartRateBeforeWarmUp: Int = 1,
    val heartRateAfterWarmUp: Int = 1,
    val warmUpDuration: LocalTime = LocalTime.of(0,20,0),
    val warmUpNote: String = "",

    val heartRateBeforeMain: Int = 1,
    val heartRateAfterMain: Int = 1,
    val mainDuration: LocalTime = LocalTime.of(1,0,0),
    val mainNote: String = "",

    val heartRateBeforeFinish: Int = 1,
    val heartRateAfterFinish: Int = 1,
    val finishDuration: LocalTime = LocalTime.of(0,10,0),
    val finishNote: String = "",

    val workCapacity: Int = 5,
    val degreeOfFatigue: Int = 5,
)