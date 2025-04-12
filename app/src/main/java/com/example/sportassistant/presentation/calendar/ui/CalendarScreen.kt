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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.data.schemas.calendar.requests.GetCalendarMonthData
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CalendarMonthData
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.ComprehensiveExamination
import com.example.sportassistant.domain.model.EventData
import com.example.sportassistant.domain.model.EventType
import com.example.sportassistant.domain.model.MedExamination
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.domain.model.SFPResult
import com.example.sportassistant.domain.model.TrainingCamp
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.calendar.viewmodel.CalendarViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionDayViewModel
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@Composable
fun CalendarScreen(
    navController: NavController,
    titleViewModel: TitleViewModel,
    calendarViewModel: CalendarViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by calendarViewModel.uiState.collectAsState()
    val daysOfWeek = stringArrayResource(id = R.array.days_of_week).toList()
    val months = stringArrayResource(id = R.array.months).toList()
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val day by remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    LaunchedEffect(key1 = currentMonth, key2 = currentYear, key3 = day) {
        if (currentMonth+1 != LocalDate.now().monthValue) {
            calendarViewModel.getMonthData(
                data = GetCalendarMonthData(
                    day = null,
                    month = currentMonth+1,
                    year = currentYear
                )
            )
        } else {
        calendarViewModel.getMonthData(
            data = GetCalendarMonthData(
                day = day,
                month = currentMonth+1,
                year = currentYear
            )
        )
            }
    }
    val calendarDataState by calendarViewModel.getMonthDataResponse.observeAsState()
    val daysInMonth = getDaysInMonth(currentMonth, Calendar.getInstance().get(Calendar.YEAR))
    val monthText = if (currentYear == Calendar.getInstance().get(Calendar.YEAR)) {
        months[currentMonth]
    } else {
        "${months[currentMonth]}, $currentYear"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.large,
                )
                .heightIn(max = 600.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
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
                if (calendarDataState is ApiResponse.Success) {
                    val data = (calendarDataState as ApiResponse.Success<CalendarMonthData?>).data!!
                    val currentCompetitionStartDate = data.competition?.startDate
                    val currentCompetitionEndDate = data.competition?.endDate

                    val currentCampStartDate = data.camp?.startDate
                    val currentCampEndDate = data.camp?.endDate

                    val eventDays = data.eventDays.keys.toList()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(daysInMonth) { day ->
                            val date = LocalDate.of(currentYear, currentMonth + 1, day)
                            val isCompetitionDay =
                                if (data.competition != null) {
                                    date in currentCompetitionStartDate!!..currentCompetitionEndDate!!
                                } else {
                                    false
                                }
                            val isCampDay =
                                if (data.camp != null) {
                                    date in currentCampStartDate!!..currentCampEndDate!!
                                } else {
                                    false
                                }
                            val hasEvent = eventDays.contains(date)
                            val isSelected = date == uiState.selectedDay
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
                                isSelected = isSelected,
                                onDayClick = {
                                    calendarViewModel.setSelectedDate(date)
                                },
                            )
                        }
                    }
                } else if (calendarDataState is ApiResponse.Loading) {
                    Column(
                        modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Loader(Modifier.heightIn(max = 150.dp))
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Green.copy(alpha = 0.3f))
            )
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = " - " + stringResource(R.string.current_competitions),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
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
        if (uiState.selectedDay != null && calendarDataState is ApiResponse.Success) {
            val data = (calendarDataState as ApiResponse.Success<CalendarMonthData?>).data!!
            val events = (data.eventDays[uiState.selectedDay] ?: listOf()).toMutableList()

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                    text = stringResource(R.string.day_events),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(thickness = 2.dp)
                val calendarText = stringResource(R.string.nav_bar_calendar_text)
                events.forEach{ event ->
                    ListItem(
                        text = event.name,
                        onDetailClick = {
                            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            when (event.dates.size) {
                                1 -> {
                                    val title = event.dates[0].format(dateFormatter)
                                    titleViewModel.setTitle(title)
                                }
                                2 -> {
                                    val start = event.dates[0].format(dateFormatter)
                                    val end = event.dates[1].format(dateFormatter)
                                    titleViewModel.setTitle("$start - $end")
                                }
                                0 -> {
                                    titleViewModel.setTitle(calendarText)
                                }
                            }
                            updateApplicationState(event)
                            navigateToDetails(navController, event)
                        }
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                    text = stringResource(R.string.day_note),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(thickness = 2.dp)
                val note = data.dayNotes.get(uiState.selectedDay)
                Row(
                    modifier = modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = note?.text ?: stringResource(R.string.empty_text),
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 5,
                    )
                    Icon(
                        painter = painterResource(R.drawable.pencil),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            if (note == null) {
                                ApplicationState.setNoteDate(uiState.selectedDay!!)
                                navController.navigate(HomeRoutes.NotesAdd.route)
                            } else {
                                val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                titleViewModel.setTitle(note.date.format(dateFormatter))
                                ApplicationState.setSelectedNote(note)
                                navController.navigate(HomeRoutes.NotesInfo.route)
                            }
                        },
                    )
                }
            }
        }
    }
}

fun navigateToDetails(navController: NavController, event: EventData) {
    when (event.type) {
        EventType.COMPETITION -> {
            navController.navigate(HomeRoutes.CompetitionInfo.route)
        }
        EventType.CAMP -> {
            navController.navigate(HomeRoutes.TrainingCampsInfo.route)
        }
        EventType.OFP -> {
            navController.navigate(HomeRoutes.OFPResultsInfo.route)
        }
        EventType.SFP -> {
            navController.navigate(HomeRoutes.SFPResultsInfo.route)
        }
        EventType.MED -> {
            navController.navigate(HomeRoutes.MedExaminationInfo.route)
        }
        EventType.COMPREHENSIVE -> {
            navController.navigate(HomeRoutes.ComprehensiveExaminationInfo.route)
        }
    }
}

fun updateApplicationState(event: EventData) {
    when (event.type) {
        EventType.COMPETITION -> {
            ApplicationState.setSelectedCompetition(
                Competition(
                    id = event.id,
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now(),
                    location = "",
                    notes = "",
                    name = "",
                )
            )
        }
        EventType.CAMP -> {
            ApplicationState.setSelectedCamp(
                TrainingCamp(
                    id = event.id,
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now(),
                    location = "",
                    notes = "",
                    goals = "",
                )
            )
        }
        EventType.OFP -> {
            ApplicationState.setSelectedOFP(
                OFPResult(
                    id = event.id,
                    date = LocalDate.now(),
                    notes = "",
                    goals = "",
                    categoryId = UUID.randomUUID(),
                    result = 0f,
                )
            )
        }
        EventType.SFP -> {
            ApplicationState.setSelectedSFP(
                SFPResult(
                    id = event.id,
                    date = LocalDate.now(),
                    notes = "",
                    goals = "",
                    categoryId = UUID.randomUUID(),
                    result = 0f,
                )
            )
        }
        EventType.MED -> {
            ApplicationState.setSelectedMedExamination(
                MedExamination(
                    id = event.id,
                    date = LocalDate.now(),
                    institution = "",
                    methods = "",
                    recommendations = "",
                )
            )
        }
        EventType.COMPREHENSIVE -> {
            ApplicationState.setSelectedComprehensiveExamination(
                ComprehensiveExamination(
                    id = event.id,
                    date = LocalDate.now(),
                    institution = "",
                    methods = "",
                    recommendations = "",
                    specialists = "",
                )
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
    isSelected: Boolean,
    onDayClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = when {
                    isCompetitionDay -> Color.Green.copy(alpha = 0.2f)
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
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = Color(0xFF90CAF9).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(100)
                    )
            )
        }
        Text(
            text = day.toString(),
            fontSize = 18.sp,
        )
    }
}

fun getDaysInMonth(month: Int, year: Int): List<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..maxDay).toList()
}

@Composable
fun ListItem(
    text: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = 10.dp, bottom = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(end = 15.dp),
        )
        Text(
            modifier = Modifier.clickable { onDetailClick() },
            text = stringResource(R.string.see_details),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.Blue,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

@Composable
fun ClearCalendar(
    daysInMonth: List<Int>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(daysInMonth) { day ->
            DayCell(
                day = day,
                isCompetitionDay = false,
                competitionShape = RoundedCornerShape(0),
                campShape = RoundedCornerShape(0),
                isCampDay = false,
                hasEvent = false,
                isSelected = false,
                onDayClick = {},
            )
        }
    }
}