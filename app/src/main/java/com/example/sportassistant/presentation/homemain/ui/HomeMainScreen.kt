package com.example.sportassistant.presentation.homemain.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.Route
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.StyledButtonListItem
import org.koin.androidx.compose.get

@Composable
fun HomeMainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val items: HashMap<Route, String> = getRouteForMenuItemMap()
        val itemsLen: Int = items.size - 1
        Column {
            items.entries.withIndex().forEach { (index, entry) ->
                if (entry.key.route == "train_diary") {
                    Spacer(modifier = Modifier.height(40.dp))
                    StyledButtonListItem (
                        text = entry.value,
                        onClick = {
                            Log.d("Navigation", "to -> ${entry.key.route}")
                        },
                        cornerShape = MaterialTheme.shapes.large,
                        border = Border.None,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                } else {
                    StyledButtonListItem(
                        text = entry.value,
                        onClick = {
                            navController.navigate(entry.key.route)
                            Log.d("Navigation", "to -> ${entry.key.route}")
                        },
                        cornerShape = getCornerShape(index, itemsLen),
                        border = getBorder(index, itemsLen),
                        modifier = if (index == 0) Modifier.padding(top = 20.dp) else Modifier
                    )
                }
            }
        }
    }
}

fun getBorder(index: Int, lastIndex: Int): Border {
    if (index == lastIndex - 1) {
        return Border.None
    }
    return Border.Bottom
}

@Composable
fun getCornerShape(index: Int, lastIndex: Int): CornerBasedShape {
    if (index == 0) {
        return MaterialTheme.shapes.large.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp),
        )
    }
    if (index == lastIndex - 1) {
        return MaterialTheme.shapes.large.copy(
            topEnd = CornerSize(0.dp),
            topStart = CornerSize(0.dp),
        )
    }
    return RoundedCornerShape(0.dp)
}

@Composable
private fun getRouteForMenuItemMap(): HashMap<Route, String> {
    return linkedMapOf(
        HomeRoutes.Competitions to stringResource(R.string.home_list_item_competition_calendar),
        HomeRoutes.TrainingCamps to stringResource(R.string.home_list_item_working_process),
        HomeRoutes.OFPResults to stringResource(R.string.home_list_item_ofp_testing),
        Route("cfp_testing") to stringResource(R.string.home_list_item_cfp_testing),
        Route("ant_params") to stringResource(R.string.home_list_item_ant_params),
        Route("mc_calendar") to stringResource(R.string.home_list_item_mc_calendar),
        Route("med_exam") to stringResource(R.string.home_list_item_med_exam),
        Route("complex_exam") to stringResource(R.string.home_list_item_complex_exam),
        Route("note") to stringResource(R.string.home_list_item_note),
        Route("train_diary") to stringResource(R.string.home_list_item_train_diary)
    )
}
