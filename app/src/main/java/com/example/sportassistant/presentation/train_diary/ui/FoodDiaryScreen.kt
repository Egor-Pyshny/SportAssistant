package com.example.sportassistant.presentation.train_diary.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.RowScrollWheel
import com.example.sportassistant.presentation.components.StyledButtonListItem
import com.example.sportassistant.presentation.train_diary.viewmodel.TrainDiaryViewModel
import org.koin.androidx.compose.get

@Composable
fun FoodDiaryScreen(
    navController: NavController,
    viewModel: TrainDiaryViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Box (
            modifier = Modifier.weight(1f)
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = modifier
                        .fillMaxWidth().padding(top = 20.dp, bottom = 35.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                        text = stringResource(R.string.day_water_text),
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                    )
                    RowScrollWheel(
                        items = (0..20).map { "${it/2f}" },
                        initialState = uiState.dayWater,
                        onSelectedChanges = { index ->
                            viewModel.setDayWater(index)
                        }
                    )
                }
                uiState.dayMeals.forEach {
                    StyledButtonListItem(
                        text = stringResource(R.string.meal_number_text)+" "+it.id.toString(),
                        onClick = {
                            navController.navigate(HomeRoutes.Meal.route.replace("{id}", "${it.id}"))
                        },
                        cornerShape = MaterialTheme.shapes.large,
                        border = Border.None,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate(HomeRoutes.Meal.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom=30.dp, end=5.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus_icon),
                    null,
                )
            }

        }
    }
}