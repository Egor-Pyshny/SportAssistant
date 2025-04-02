package com.example.sportassistant.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import kotlin.math.abs

@Composable
fun RowScrollWheel(
    items: List<String>,
    onSelectedChanges: (index: Int) -> Unit,
    initialState: Int,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState(initialState)
    val snapState = rememberSnapFlingBehavior(scrollState)
    LaunchedEffect(Unit) {
        scrollState.animateScrollToItem(initialState-1)
    }
    var componentHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val selectedIndex by remember {
        derivedStateOf {
            val layoutInfo = scrollState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf 0

            val center = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2
            val index = layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    abs((item.offset + item.size / 2) - center)
                }?.index ?: 0
            onSelectedChanges(index)
            index
        }
    }
    Box (
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .zIndex(5f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(componentHeight - componentHeight/6)
                    .padding(top = componentHeight/12)
                    .fillMaxHeight(),
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .height(componentHeight - componentHeight/6)
                        .padding(top = componentHeight/12),
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(componentHeight - componentHeight/6)
                    .padding(top = componentHeight/12)
                    .fillMaxHeight(),

                contentAlignment = Alignment.CenterEnd
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .height(componentHeight - componentHeight/6)
                        .padding(top = componentHeight/12),
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }
        }
        Row(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 10.dp
            ).background(
                color = Color.White,
                shape = MaterialTheme.shapes.large,
            ).fillMaxWidth(),
        ) {
            LazyRow(
                modifier = Modifier.padding(
                    start = 55.dp,
                    end = 55.dp,
                ).fillMaxWidth().onGloballyPositioned {
                    componentHeight = with(density) {
                        it.size.height.toDp()
                    }
                },
                state = scrollState,
                flingBehavior = snapState,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(items.size+2, key = {it}) { item ->
                    if (item == 0 || item == items.size+1) {
                        Text(
                            style = MaterialTheme.typography.headlineMedium,
                            text = "",
                            modifier = Modifier.padding(vertical = 15.dp).width(80.dp)
                        )
                    } else {
                        Text(
                            style =
                            if (item == selectedIndex)
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            else
                                MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Thin
                                ),
                            modifier = Modifier.alpha(
                                if (item == selectedIndex) 1f else 0.5f
                            ).padding(vertical = 15.dp).width(80.dp),
                            text = items[item-1],
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}