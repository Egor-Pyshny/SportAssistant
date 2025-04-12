package com.example.sportassistant.presentation.competition_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.ErrorScreen
import com.example.sportassistant.presentation.components.ListItem
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.MenuItem
import com.example.sportassistant.presentation.components.StyledButtonListWithDropDownMenu
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun CompetitionCalendarMainScreen(
    navController: NavController,
    tabsViewModel: TabsViewModel,
    competitionViewModel: CompetitionViewModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val tabIndex by tabsViewModel.uiState.collectAsState()
    val competitionsResponse by getCompetitions(
        competitionViewModel = competitionViewModel,
        tabIndex = tabIndex,
    )
    LaunchedEffect(Unit) {
        competitionViewModel.getCompetitions(tabIndex)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (competitionsResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                val data = (competitionsResponse as ApiResponse.Success<List<Competition>?>).data
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
                            ApplicationState.setSelectedCompetition(data[index])
                            onClick()
                        },
                        menuItems = listOf(
                            MenuItem(
                                text = stringResource(R.string.get_detailed_info),
                                onClick = {item ->
                                    navController.navigate(HomeRoutes.CompetitionInfo.route)
                                }
                            ),
                            MenuItem(
                                text = stringResource(R.string.delete_item),
                                onClick = {item ->
                                    competitionViewModel.deleteCompetition(item.key as UUID)
                                }
                            ),
                        ),
                    )
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom=30.dp, end = screenSizeProvider.getEdgeSpacing()+5.dp),
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
                ErrorScreen(competitionsResponse as ApiResponse.Failure)
            }
            else -> {}
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun getCompetitions(
    competitionViewModel: CompetitionViewModel,
    tabIndex: Int,
): State<ApiResponse<List<Competition>?>?> {
    val deleteState by competitionViewModel.deleteCompetitionResponse.observeAsState()

    if (deleteState != null) {
        when (deleteState) {
            is ApiResponse.Loading -> {
                return mutableStateOf(ApiResponse.Loading)
            }
            is ApiResponse.Success -> {
                competitionViewModel.clearDelete()
                competitionViewModel.getCompetitions(tabIndex)
            }
            is ApiResponse.Failure -> {
                return mutableStateOf(deleteState as ApiResponse.Failure)
            }
            else -> {}
        }
    }
    return when (tabIndex) {
        0 -> {
            competitionViewModel.getCompetitionsPrevResponse.observeAsState()
        }
        1 -> {
            competitionViewModel.getCompetitionsCurrentResponse.observeAsState()
        }
        2 -> {
            competitionViewModel.getCompetitionsNextResponse.observeAsState()
        }
        else -> {
            throw Error()
        }
    }
}