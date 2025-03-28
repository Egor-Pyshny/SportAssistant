package com.example.sportassistant.presentation.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionDayViewModel
import org.koin.androidx.compose.get
import java.time.LocalDate
import java.util.Calendar

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val daysOfWeek = stringArrayResource(id = R.array.days_of_week).toList()
    val months = stringArrayResource(id = R.array.months).toList()
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    val daysInMonth = getDaysInMonth(currentMonth, Calendar.getInstance().get(Calendar.YEAR))
    val monthText = if (currentYear == Calendar.getInstance().get(Calendar.YEAR)) {
        months[currentMonth]
    } else {
        "${months[currentMonth]}, $currentYear"
    }

    val currentCompetitionStartDate = LocalDate.now().minusDays(10)
    val currentCompetitionEndDate = LocalDate.now().minusDays(5)

    val currentCampStartDate = LocalDate.now().minusDays(7)
    val currentCampEndDate = LocalDate.now().minusDays(2)

    val eventDays = listOf<LocalDate>()

    Column(
        modifier = Modifier.padding(
            start = screenSizeProvider.getEdgeSpacing(),
            end = screenSizeProvider.getEdgeSpacing(),
        )
    ) {
        Column(
            modifier = Modifier.padding(
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
                            textAlign = TextAlign.Center
                        )
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(daysInMonth) { day ->
                        val date = LocalDate.of(currentYear, currentMonth+1, day)
                        val isCompetitionDay =
                            date in currentCompetitionStartDate..currentCompetitionEndDate
                        val isCampDay =
                            date in currentCampStartDate..currentCampEndDate
                        val hasEvent = eventDays.contains(date)
                        DayCell(
                            day = day,
                            isCompetitionDay = isCompetitionDay,
                            competitionShape = getShape(
                                date,
                                currentCompetitionStartDate,
                                currentCompetitionEndDate,
                            ),
                            campShape = getShape(
                                date,
                                currentCampStartDate,
                                currentCampEndDate,
                            ),
                            isCampDay = isCampDay,
                            hasEvent = hasEvent,
                            onDayClick = {

                            },
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = " - " + stringResource(R.string.current_competitions),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Blue.copy(alpha = 0.5f))
            )
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = " - " + stringResource(R.string.current_camps),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun getShape(
    date: LocalDate,
    start: LocalDate?,
    end: LocalDate?,
): RoundedCornerShape {
    val cornerRadius = 50
    if (date == start) {
        return RoundedCornerShape(
            topStartPercent = cornerRadius,
            bottomStartPercent = cornerRadius,
            topEndPercent = 0,
            bottomEndPercent = 0,
        )
    }
    if (date == end) {
        return RoundedCornerShape(
            topStartPercent = 0,
            bottomStartPercent = 0,
            topEndPercent = cornerRadius,
            bottomEndPercent = cornerRadius,
        )
    }
    return RoundedCornerShape(
        topStartPercent = 0,
        bottomStartPercent = 0,
        topEndPercent = 0,
        bottomEndPercent = 0,
    )
}


@Composable
fun DayCell(
    day: Int,
    competitionShape: RoundedCornerShape,
    campShape: RoundedCornerShape,
    isCompetitionDay: Boolean,
    isCampDay: Boolean,
    hasEvent: Boolean,
    onDayClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = when {
                    isCompetitionDay -> Color.Black.copy(alpha = 0.2f)
                    else -> Color.Transparent
                },
                shape = competitionShape,
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onDayClick() },
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (isCampDay) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = Color.Blue.copy(alpha = 0.2f),
                        shape = campShape
                    )
            )
        }
        if (hasEvent) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .padding(8.dp)
                    .background(Color.Red, CircleShape)
            )
        }
        Text(
            text = day.toString(),
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

fun getDaysInMonth(month: Int, year: Int): List<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..maxDay).toList()
}