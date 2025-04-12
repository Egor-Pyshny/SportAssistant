package com.example.sportassistant.presentation.ofp_results.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.ErrorScreen
import com.example.sportassistant.presentation.components.ListItem
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.MenuItem
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledButtonListWithDropDownMenu
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.ofp_result_add.viewmodel.OFPResultAddViewModel
import com.example.sportassistant.presentation.ofp_results.viewmodel.OFPResultsViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@Composable
fun OFPResultsScreen(
    navController: NavController,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    ofpResultsViewModel: OFPResultsViewModel = koinViewModel(),
    screenSizeProvider: WindowSizeProvider = get(),
) {
    LaunchedEffect(Unit) {
        ofpResultsViewModel.getResults()
    }
    val ofpResultsResponse by getResults(ofpResultsViewModel)
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (ofpResultsResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                val data = (ofpResultsResponse as ApiResponse.Success<List<OFPResult>?>).data
                    ?: listOf()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val listItems: List<ListItem> = data.map {
                    ListItem(
                        key = it.id,
                        item = "${it.date.format(formatter)}"
                    )
                }
                StyledButton(
                    text = stringResource(R.string.draw_graphic),
                    onClick = {
                        navController.navigate(HomeRoutes.OFPResultsGraphic.route)
                    },
                    isEnabled = true,
                    trailingIcon = R.drawable.line_chart,
                    trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                    modifier = Modifier.padding(
                        start = screenSizeProvider.getEdgeSpacing(),
                        end = screenSizeProvider.getEdgeSpacing(),
                        bottom = 25.dp,
                        top = 20.dp
                    )
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    StyledButtonListWithDropDownMenu(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = screenSizeProvider.getEdgeSpacing(),
                                end = screenSizeProvider.getEdgeSpacing(),
                                bottom = 20.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        items = listItems,
                        onClick = { index, title ->
                            ApplicationState.setSelectedOFP(data[index])
                            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
                            val date = data[index].date.format(dateFormatter)
                            titleViewModel.setTitle(date)
                            navController.navigate(HomeRoutes.OFPResultsInfo.route)
                        },
                        menuItems = listOf(
                            MenuItem(
                                text = stringResource(R.string.delete_item),
                                onClick = {item ->
                                    ofpResultsViewModel.deleteOFPResult(item.key as UUID)
                                }
                            ),
                        ),
                    )
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(HomeRoutes.OFPResultsAdd.route)
                        },
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
                ErrorScreen(ofpResultsResponse as ApiResponse.Failure)
            }
            else -> {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun getResults(
    ofpResultsViewModel: OFPResultsViewModel,
): State<ApiResponse<List<OFPResult>?>?> {
    val deleteState by ofpResultsViewModel.deleteOFPResultResponse.observeAsState()

    if (deleteState != null) {
        when (deleteState) {
            is ApiResponse.Loading -> {
                return mutableStateOf(ApiResponse.Loading)
            }
            is ApiResponse.Success -> {
                ofpResultsViewModel.clearDeleteResponse()
                ofpResultsViewModel.getResults()
            }
            is ApiResponse.Failure -> {
                return mutableStateOf(deleteState as ApiResponse.Failure)
            }
            else -> {}
        }
    }
    return ofpResultsViewModel.getOFPResultsResponse.observeAsState()
}