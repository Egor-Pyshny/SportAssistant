package com.example.sportassistant.presentation.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import java.time.LocalTime
import kotlin.math.abs


@Composable
fun TimeScrollWheel(
    hours: List<String>,
    minutes: List<String>,
    seconds: List<String>,
    initialTime: LocalTime,
    onSelectedChanges: (time: LocalTime) -> Unit,
) {
    val hourScrollState = rememberLazyListState(initialTime.hour)
    val hourSnapState = rememberSnapFlingBehavior(hourScrollState)
    val hourSelectedIndex by remember {
        derivedStateOf {
            val layoutInfo = hourScrollState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf 0

            val center = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2
            val index = layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    abs((item.offset + item.size / 2) - center)
                }?.index ?: 0
            index
        }
    }

    val minuteScrollState = rememberLazyListState(initialTime.minute)
    val minuteSnapState = rememberSnapFlingBehavior(minuteScrollState)
    val minuteSelectedIndex by remember {
        derivedStateOf {
            val layoutInfo = minuteScrollState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf 0

            val center = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2
            val index = layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    abs((item.offset + item.size / 2) - center)
                }?.index ?: 0
            index
        }
    }

    val secondScrollState = rememberLazyListState(initialTime.second)
    val secondSnapState = rememberSnapFlingBehavior(secondScrollState)
    val secondSelectedIndex by remember {
        derivedStateOf {
            val layoutInfo = secondScrollState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf 0

            val center = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2
            val index = layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    abs((item.offset + item.size / 2) - center)
                }?.index ?: 0
            index
        }
    }
    LaunchedEffect(key1 = hourSelectedIndex, key2 = minuteSelectedIndex, key3 = secondSelectedIndex) {
        onSelectedChanges(LocalTime.of(hourSelectedIndex-1, minuteSelectedIndex-1, secondSelectedIndex-1))
    }
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(0.dp)
                .zIndex(5f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(componentWidth)
                    .height(60.dp)
            ) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }
            Box(
                modifier = Modifier
                    .width(componentWidth)
                    .height(60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }
            Box(
                modifier = Modifier
                    .width(componentWidth/3)
                    .height(60.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = ":",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .width(componentWidth/3)
                    .height(60.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = ":",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                start = 55.dp,
                end = 55.dp,
            ).onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.heightIn(max = 180.dp)
                    .width(80.dp),
                state = hourScrollState,
                flingBehavior = hourSnapState,
            ) {
                items(hours.size+2) { item ->
                    if (item == 0 || item == hours.size+1) {
                        Text(
                            style = MaterialTheme.typography.headlineMedium,
                            text = "",
                            modifier = Modifier.height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                        )
                    } else {
                        Text(
                            style =
                            if (item == hourSelectedIndex)
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            else
                                MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Thin
                                ),
                            modifier = Modifier.alpha(
                                if (item == hourSelectedIndex) 1f else 0.5f
                            ).height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                            text = hours[item-1],
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.heightIn(max = 180.dp),
                state = minuteScrollState,
                flingBehavior = minuteSnapState,
            ) {
                items(minutes.size+2) { item ->
                    if (item == 0 || item == minutes.size+1) {
                        Text(
                            style = MaterialTheme.typography.headlineMedium,
                            text = "",
                            modifier = Modifier.height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                        )
                    } else {
                        Text(
                            style =
                            if (item == minuteSelectedIndex)
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            else
                                MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Thin
                                ),
                            modifier = Modifier.alpha(
                                if (item == minuteSelectedIndex) 1f else 0.5f
                            ).height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                            text = minutes[item-1],
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.heightIn(max = 180.dp)
                    .width(80.dp),
                state = secondScrollState,
                flingBehavior = secondSnapState,
            ) {
                items(seconds.size+2) { item ->
                    if (item == 0 || item == seconds.size+1) {
                        Text(
                            style = MaterialTheme.typography.headlineMedium,
                            text = "",
                            modifier = Modifier.height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                        )
                    } else {
                        Text(
                            style =
                            if (item == secondSelectedIndex)
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            else
                                MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Thin
                                ),
                            modifier = Modifier.alpha(
                                if (item == secondSelectedIndex) 1f else 0.5f
                            ).height(60.dp)
                                .width(80.dp).padding(vertical = 10.dp),
                            text = seconds[item-1],
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
