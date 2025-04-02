package com.example.sportassistant.presentation.train_diary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.RowScrollWheel
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.TimeScrollWheel
import com.example.sportassistant.presentation.train_diary.domain.Meal
import com.example.sportassistant.presentation.train_diary.viewmodel.MealViewModel
import com.example.sportassistant.presentation.train_diary.viewmodel.TrainDiaryViewModel
import org.koin.androidx.compose.get

@Composable
fun MealScreen(
    navController: NavController,
    mealId: Int?,
    diaryViewModel: TrainDiaryViewModel,
    mealViewModel: MealViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by mealViewModel.uiState.collectAsState()
    val calories by remember {
        derivedStateOf {
            (uiState.fats - 1) * 9 + (uiState.carbs + uiState.protein - 2) * 4
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth().padding(top = 20.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 25.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.appetite_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.appetit,
                onSelectedChanges = { index ->
                    mealViewModel.setAppetit(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.meal_time_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Row (
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 10.dp,
                ).background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.large,
                ).fillMaxWidth(),
            ) {
                TimeScrollWheel (
                    hours = (0..23).map { "%02d".format(it) },
                    minutes = (0..59).map { "%02d".format(it) },
                    seconds = (0..59).map { "%02d".format(it) },
                    initialTime = uiState.mealTime,
                    onSelectedChanges = { time ->
                        mealViewModel.setMealTime(time)
                    }
                )
            }
            HorizontalDivider(thickness = 2.dp)
            StyledCardTextField(
                value = uiState.note,
                label = R.string.day_note,
                onValueChange = {
                    mealViewModel.setNote(it)
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.describe_meal_text),
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                maxLines = 5,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                ),
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.meal_protein_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = remember { (0..1000).map { "$it" } },
                initialState = uiState.protein,
                onSelectedChanges = { index ->
                    mealViewModel.setProtein(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.meal_fats_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = remember { (0..1000).map { "$it" } },
                initialState = uiState.fats,
                onSelectedChanges = { index ->
                    mealViewModel.setFats(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.meal_carbs_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = remember { (0..1000).map { "$it" } },
                initialState = uiState.carbs,
                onSelectedChanges = { index ->
                    mealViewModel.setCarbs(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                ),
                text = stringResource(R.string.total_call_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 35.dp,
                ),
                text = "$calories",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StyledButton(
                text = stringResource(R.string.save_button_text),
                onClick = {
                    if (mealId != null) {
                        diaryViewModel.updateMeal(
                            Meal(
                                id = mealId,
                                appetit = uiState.appetit,
                                mealTime = uiState.mealTime,
                                note = uiState.note,
                                fats = uiState.fats,
                                carbs = uiState.carbs,
                                protein = uiState.protein
                            )
                        )
                    } else {
                        diaryViewModel.addMeal(
                            Meal(
                                id = diaryViewModel.getMealId(),
                                appetit = uiState.appetit,
                                mealTime = uiState.mealTime,
                                note = uiState.note,
                                fats = uiState.fats,
                                carbs = uiState.carbs,
                                protein = uiState.protein
                            )
                        )
                        navController.navigate(HomeRoutes.Food.route)
                    }
                },
                isEnabled = true,
                trailingIcon = R.drawable.save,
                trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                modifier = Modifier.padding(bottom = 25.dp, top = 45.dp)
            )
        }
    }
}