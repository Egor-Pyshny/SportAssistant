package com.example.sportassistant.presentation.train_diary.ui

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
fun TrainDiaryMainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val items: HashMap<Route, String> = getRouteForMenuItemMap()
    val itemsLen: Int = items.size - 1
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 20.dp,
            ).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            items.entries.withIndex().forEach { (index, entry) ->
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

@Composable
fun getCornerShape(index: Int, lastIndex: Int): CornerBasedShape {
    if (index == 0) {
        return MaterialTheme.shapes.large.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp),
        )
    }
    if (index == lastIndex) {
        return MaterialTheme.shapes.large.copy(
            topEnd = CornerSize(0.dp),
            topStart = CornerSize(0.dp),
        )
    }
    return RoundedCornerShape(0.dp)
}

private fun getBorder(index: Int, lastIndex: Int): Border {
    if (index == lastIndex) {
        return Border.None
    }
    return Border.Bottom
}

@Composable
private fun getRouteForMenuItemMap(): HashMap<Route, String> {
    return linkedMapOf(
        HomeRoutes.Sleep to stringResource(R.string.diary_list_item_sleep),
        HomeRoutes.Food to stringResource(R.string.diary_list_item_food),
        HomeRoutes.Preparations to stringResource(R.string.diary_list_item_preparations),
        HomeRoutes.Activity to stringResource(R.string.diary_list_item_activity)
    )
}