package com.example.sportassistant.presentation.ant_params.ui

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
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.ant_params.viewmodel.AnthropometricParamsViewModel
import com.example.sportassistant.presentation.components.ListItem
import com.example.sportassistant.presentation.components.MenuItem
import com.example.sportassistant.presentation.components.StyledButtonListWithDropDownMenu
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@Composable
fun AnthropometricParamsScreen(
    navController: NavController,
    anthropometricParamsViewModel: AnthropometricParamsViewModel,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    LaunchedEffect(Unit) {
        anthropometricParamsViewModel.getAnthropometricParams()
    }
    val anthropometricParamsResultsResponse by getResults(anthropometricParamsViewModel)
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (anthropometricParamsResultsResponse) {
            is ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiResponse.Success -> {
                val data = (anthropometricParamsResultsResponse as ApiResponse.Success<List<AnthropometricParams>?>).data
                    ?: listOf()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val listItems: List<ListItem> = data.map {
                    ListItem(
                        key = it.id,
                        item = "${it.date.format(formatter)}"
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
                            anthropometricParamsViewModel.setSelectedAnthropometricParams(data[index])
                            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
                            val date = data[index].date.format(dateFormatter)
                            titleViewModel.setTitle(date)
                            anthropometricParamsViewModel.getAnthropometricParamsInfo(data[index].id)
                            navController.navigate(HomeRoutes.AnthropometricParamsInfo.route)
                        },
                        menuItems = listOf(
                            MenuItem(
                                text = stringResource(R.string.delete_item),
                                onClick = {item ->
                                    anthropometricParamsViewModel.deleteAnthropometricParams(item.key as UUID)
                                }
                            ),
                        ),
                    )
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(HomeRoutes.AnthropometricParamsAdd.route)
                        },
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
                    text = (anthropometricParamsResultsResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun getResults(
    anthropometricParamsViewModel: AnthropometricParamsViewModel,
): State<ApiResponse<List<AnthropometricParams>?>?> {
    val deleteState by anthropometricParamsViewModel.deleteAnthropometricParamsResponse.observeAsState()

    if (deleteState != null) {
        when (deleteState) {
            is ApiResponse.Loading -> {
                return mutableStateOf(ApiResponse.Loading)
            }
            is ApiResponse.Success -> {
                anthropometricParamsViewModel.setShouldRefetch(true)
                anthropometricParamsViewModel.clearDeleteResponse()
                anthropometricParamsViewModel.getAnthropometricParams()
            }
            is ApiResponse.Failure -> {
                throw Error()
            }
            else -> {}
        }
    }
    return anthropometricParamsViewModel.getAnthropometricParamsResponse.observeAsState()
}