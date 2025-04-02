package com.example.sportassistant.presentation.train_diary.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get

@Composable
fun PreparationsScreen(
    navController: NavHostController,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val items: HashMap<String, String> = getRouteForMenuItemMap()
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
                        navController.navigate(entry.key)
                        titleViewModel.setTitle(entry.value)
                        Log.d("Navigation", "to -> ${entry.key}")
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
private fun getCornerShape(index: Int, lastIndex: Int): CornerBasedShape {
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
private fun getRouteForMenuItemMap(): HashMap<String, String> {
    return linkedMapOf(
        HomeRoutes.Preparation.route.replace("{type}", PreparationType.GENERAL.name) to stringResource(R.string.general_prep_text),
        HomeRoutes.Preparation.route.replace("{type}", PreparationType.SPEC.name) to stringResource(R.string.special_prep_text),
        HomeRoutes.Preparation.route.replace("{type}", PreparationType.PRED_COMP.name) to stringResource(R.string.pred_comp_prep_text),
        HomeRoutes.Preparation.route.replace("{type}", PreparationType.COMP.name) to stringResource(R.string.comp_prep_text),
        HomeRoutes.Preparation.route.replace("{type}", PreparationType.TRANSITIONAL.name) to stringResource(R.string.transitional_prep_text)
    )
}

enum class PreparationType {
    GENERAL, SPEC, PRED_COMP, COMP, TRANSITIONAL
}
