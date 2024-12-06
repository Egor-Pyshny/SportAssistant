package com.example.sportassistant.presentation.homemain.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.Route
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.StyledListItem
import com.example.sportassistant.presentation.theme.homeScreenListBackground
import java.time.format.TextStyle

@Composable
fun HomeMainScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
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
                    StyledListItem (
                        text = entry.value,
                        onClick = {
                            Log.d("Navigation", "to -> $entry.key")
                        },
                        cornerShape = MaterialTheme.shapes.large,
                        border = Border.None,
                        modifier = Modifier.padding(bottom = 60.dp)
                    )
                } else {
                    StyledListItem(
                        text = entry.value,
                        onClick = {
                            Log.d("Navigation", "to -> ${entry.key}")
                        },
                        cornerShape = getCornerShape(index, itemsLen),
                        border = getBorder(index, itemsLen),
                        modifier = if (index == 0) Modifier.padding(top = 60.dp) else Modifier
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
        Route("competition_calendar") to stringResource(R.string.home_list_item_competition_calendar),
        Route("working_process") to stringResource(R.string.home_list_item_working_process),
        Route("ofp_testing") to stringResource(R.string.home_list_item_ofp_testing),
        Route("cfp_testing") to stringResource(R.string.home_list_item_cfp_testing),
        Route("ant_params") to stringResource(R.string.home_list_item_ant_params),
        Route("mc_calendar") to stringResource(R.string.home_list_item_mc_calendar),
        Route("med_exam") to stringResource(R.string.home_list_item_med_exam),
        Route("complex_exam") to stringResource(R.string.home_list_item_complex_exam),
        Route("note") to stringResource(R.string.home_list_item_note),
        Route("train_diary") to stringResource(R.string.home_list_item_train_diary)
    )
}
