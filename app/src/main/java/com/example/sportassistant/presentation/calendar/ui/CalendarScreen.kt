package com.example.sportassistant.presentation.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionDayViewModel
import org.koin.androidx.compose.get
import java.util.Calendar

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val context = LocalContext.current
    val daysOfWeek = stringArrayResource(id = R.array.days_of_week).toList()
    val months = stringArrayResource(id = R.array.months).toList()
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var selectedDates by remember { mutableStateOf(setOf<Int>()) }
    var eventDates by remember { mutableStateOf(setOf<Int>()) }

    val daysInMonth = getDaysInMonth(currentMonth, Calendar.getInstance().get(Calendar.YEAR))
    val monthText = if (currentYear == Calendar.getInstance().get(Calendar.YEAR)) {
        months[currentMonth]
    } else {
        "${months[currentMonth]}, $currentYear"
    }
    Column (
        modifier = Modifier.padding(
            start = screenSizeProvider.getEdgeSpacing(),
            end = screenSizeProvider.getEdgeSpacing(),
            top = 20.dp,
        ).background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.large,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp,
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    if (currentMonth == 0) {
                        currentMonth = 11
                        currentYear -= 1
                    } else {
                        currentMonth -= 1
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.chevron_left),
                    contentDescription = null
                )
            }
            Text(
                text = monthText,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF828282),
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    if (currentMonth == 11) {
                        currentMonth = 0
                        currentYear += 1
                    } else {
                        currentMonth += 1
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.chevron_right),
                    contentDescription = null
                )
            }
        }
        HorizontalDivider(thickness = 1.dp)
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(daysInMonth) { day ->
                    val isSelected = selectedDates.contains(day)
                    val hasEvent = eventDates.contains(day)
                    DayCell(
                        day = day,
                        isSelected = isSelected,
                        hasEvent = hasEvent,
                        onDayClick = {
                            selectedDates = if (isSelected) {
                                selectedDates - day
                            } else {
                                selectedDates + day
                            }
                        },
                        onDayLongClick = {
                            eventDates = if (hasEvent) {
                                eventDates - day
                            } else {
                                eventDates + day
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DayCell(
    day: Int,
    isSelected: Boolean,
    hasEvent: Boolean,
    onDayClick: () -> Unit,
    onDayLongClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                color = when {
                    isSelected -> Color.Green
                    hasEvent -> Color.Blue
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(100)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onDayLongClick() },
                    onTap = { onDayClick() },
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            fontSize = 18.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

fun getDaysInMonth(month: Int, year: Int): List<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..maxDay).toList()
}