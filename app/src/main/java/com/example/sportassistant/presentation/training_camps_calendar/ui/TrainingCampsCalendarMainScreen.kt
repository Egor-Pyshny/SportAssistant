package com.example.sportassistant.presentation.training_camps_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.TrainingCamp
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.ListItem
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.MenuItem
import com.example.sportassistant.presentation.components.StyledButtonListWithDropDownMenu
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun TrainingCampsCalendarMainScreen(
    navController: NavController,
    tabsViewModel: TabsViewModel,
    trainingCampsViewModel: TrainingCampsViewModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val tabIndex by tabsViewModel.uiState.collectAsState()
    val campsResponse by getCamps(
        trainingCampsViewModel = trainingCampsViewModel,
        tabIndex = tabIndex,
    )
    LaunchedEffect(Unit) {
        trainingCampsViewModel.getCamps(tabIndex)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (campsResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                val data = (campsResponse as ApiResponse.Success<List<TrainingCamp>?>).data
                    ?: listOf()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val listItems: List<ListItem> = data.map {
                    ListItem(
                        key = it.id,
                        item = "${it.startDate.format(formatter)} - ${it.endDate.format(formatter)}"
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    StyledButtonListWithDropDownMenu(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = screenSizeProvider.getEdgeSpacing(),
                                end = screenSizeProvider.getEdgeSpacing(),
                                top = 20.dp,
                                bottom = 20.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        items = listItems,
                        onClick = { index, title ->
                            ApplicationState.setSelectedCamp(data[index])
                            onClick()
                        },
                        menuItems = listOf(
                            MenuItem(
                                text = stringResource(R.string.get_detailed_info),
                                onClick = {item ->
                                    navController.navigate(HomeRoutes.TrainingCampsInfo.route)
                                }
                            ),
                            MenuItem(
                                text = stringResource(R.string.delete_item),
                                onClick = {item ->
                                    trainingCampsViewModel.deleteCamp(item.key as UUID)
                                }
                            ),
                        ),
                    )
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(30.dp),
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
            is ApiResponse.Failure -> {
                Text(
                    text = (campsResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun getCamps(
    trainingCampsViewModel: TrainingCampsViewModel,
    tabIndex: Int,
): State<ApiResponse<List<TrainingCamp>?>?> {
    val deleteState by trainingCampsViewModel.deleteCampResponse.observeAsState()

    if (deleteState != null) {
        when (deleteState) {
            is ApiResponse.Loading -> {
                return mutableStateOf(ApiResponse.Loading)
            }
            is ApiResponse.Success -> {
                trainingCampsViewModel.clearDeleteResponse()
                trainingCampsViewModel.getCamps(tabIndex)
            }
            is ApiResponse.Failure -> {
                throw Error()
            }
            else -> {}
        }
    }
    return when (tabIndex) {
        0 -> {
            trainingCampsViewModel.getCampsPrevResponse.observeAsState()
        }
        1 -> {
            trainingCampsViewModel.getCampsCurrentResponse.observeAsState()
        }
        2 -> {
            trainingCampsViewModel.getCampsNextResponse.observeAsState()
        }
        else -> {
            throw Error()
        }
    }
}