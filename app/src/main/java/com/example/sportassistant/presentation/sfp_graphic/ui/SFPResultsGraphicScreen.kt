package com.example.sportassistant.presentation.sfp_graphic.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.presentation.components.DateRangePickerHeadline
import com.example.sportassistant.presentation.components.GetDropdownTrailingIcon
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.sfp_graphic.domain.SFPResultsGraphicUiState
import com.example.sportassistant.presentation.sfp_graphic.viewmodel.SFPResultsGraphicViewModel
import com.example.sportassistant.presentation.sfp_results.viewmodel.SFPResultsViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import org.koin.androidx.compose.get
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SFPResultsGraphicScreen(
    sfpResultsViewModel: SFPResultsViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    sfpResultsGraphicViewModel: SFPResultsGraphicViewModel = viewModel(),
) {
    val uiState by sfpResultsGraphicViewModel.uiState.collectAsState()
    val getGraphicData by sfpResultsViewModel.getGraphicDataResponse.observeAsState()
    var expandedDate by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }
    var missingDate by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = System.currentTimeMillis(),
        initialSelectedEndDateMillis = null,
        initialDisplayMode = DisplayMode.Input,
    )
    var prevState = SFPResultsGraphicUiState()
    val categoriesResponse by sfpResultsViewModel.getCategoriesResponse.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            sfpResultsViewModel.clearGraphicDataResponse()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedDate,
            onExpandedChange = { expandedDate = it },
            modifier = Modifier.fillMaxWidth().padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
//                top = 15.dp,
                bottom = 6.dp,
            ).background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large,
            ),

            ) {
            StyledCardTextField(
                value = uiState.datePeriod.ifEmpty { stringResource(R.string.ant_params_date_period) },
                onValueChange = { },
                enabled = false,
                modifier = Modifier.menuAnchor().padding(
                    start = 15.dp,
                    end = 10.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                ),
                readOnly = true,
                trailingIcon = {
                    GetDropdownTrailingIcon(expandedDate)
                },
            )
            DropdownMenu (
                expanded = expandedDate,
                onDismissRequest = { expandedDate = false },
                modifier = Modifier.exposedDropdownSize().heightIn(max = 200.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.custom_date_period),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        showDialog = true
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.fillMaxWidth()
                )
                stringArrayResource(R.array.date_periods).forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            calculatePeriod(sfpResultsGraphicViewModel, index)
                            sfpResultsGraphicViewModel.setDatePeriod(option)
                            expandedDate = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            if (showDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (
                                dateRangePickerState.selectedEndDateMillis != null
                                && dateRangePickerState.selectedStartDateMillis != null
                            ) {
                                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                                val startDate = dateRangePickerState.selectedStartDateMillis?.let { it ->
                                    val date = LocalDate.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                                    sfpResultsGraphicViewModel.setStartDate(date)
                                    date.format(formatter)
                                } ?: ""

                                val endDate = dateRangePickerState.selectedEndDateMillis?.let {
                                    val date = LocalDate.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                                    sfpResultsGraphicViewModel.setEndDate(date)
                                    date.format(formatter)
                                } ?: ""
                                sfpResultsGraphicViewModel.setDatePeriod("$startDate - $endDate")
                                showDialog = false
                                expandedDate = false
                                missingDate = false
                            } else {
                                missingDate = true
                            }
                        }) {
                            Text(stringResource(R.string.confirm))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            dateRangePickerState.setSelection(
                                startDateMillis = System.currentTimeMillis(),
                                endDateMillis = null,
                            )
                        }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DateRangePicker(
                        state = dateRangePickerState,
                        title = {},
                        modifier = Modifier,
                        showModeToggle = false,
                        headline = {
                            DateRangePickerHeadline(
                                selectedStartDateMillis = dateRangePickerState.selectedStartDateMillis,
                                selectedEndDateMillis = dateRangePickerState.selectedEndDateMillis,
                                dateFormatter = DatePickerDefaults.dateFormatter(),
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                                ),
                                startDatePlaceholder = { Text(text = stringResource(R.string.start_date)) },
                                endDatePlaceholder = { Text(text = stringResource(R.string.end_date)) },
                                datesDelimiter = { Text(text = "-") }
                            )
                        },
                    )
                    if (missingDate) {
                        val text =
                            if (dateRangePickerState.selectedStartDateMillis == null)
                                stringResource(R.string.missing_start_date_error)
                            else if (dateRangePickerState.selectedEndDateMillis == null)
                                stringResource(R.string.missing_end_date_error)
                            else
                                stringResource(R.string.missing_date_error)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = text, color = Color.Red)
                        }
                    }
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedCategory,
            onExpandedChange = { expandedCategory = it },
            modifier = Modifier.fillMaxWidth().padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 15.dp,
                bottom = 6.dp,
            ).background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large,
            ),
        ) {
            StyledCardTextField(
                value = uiState.category.ifEmpty { stringResource(R.string.pick_category_placeholder) },
                onValueChange = { },
                enabled = false,
                modifier = Modifier.menuAnchor().padding(
                    start = 15.dp,
                    end = 10.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                ),
                readOnly = true,
                trailingIcon = {
                    GetDropdownTrailingIcon(expandedCategory)
                },
            )
            DropdownMenu (
                expanded = expandedCategory,
                onDismissRequest = { expandedCategory = false },
                modifier = Modifier.exposedDropdownSize().heightIn(max = 200.dp)
            ) {
                when (categoriesResponse) {
                    is ApiResponse.Loading -> {
                        Loader()
                    }
                    is ApiResponse.Success -> {
                        val categories = (categoriesResponse as ApiResponse.Success<List<CategoryModel>?>).data
                            ?: listOf()
                        categories.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    sfpResultsGraphicViewModel.setCategory(option.name)
                                    sfpResultsGraphicViewModel.setSelectedCategory(option.id)
                                    expandedCategory = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    else -> {}
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f).padding(
                top = 20.dp,
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ).heightIn(min = 700.dp),
            verticalArrangement = Arrangement.Top
        ) {
            when (getGraphicData) {
                is ApiResponse.Loading -> {
                    Loader()
                }
                is ApiResponse.Success -> {
                    val points = (getGraphicData as ApiResponse.Success<List<GraphicPoint>?>).data ?: listOf()
                    if (points.size > 1) {
//                        val minDate = points.minOfOrNull{ it.key }!!
//                        val maxDate = points.maxOfOrNull{ it.key }!!
//                        val middleDates = getMiddleDates(points.map { it.key }, minDate, maxDate)
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
//                        val stringDates = middleDates.map { it.format(formatter) }
                        val values = points.map { it.value.toDouble() }
                        val maxValues = values.max()
                        val indicatorsCount = maxValues.toInt() / 5 + 1
                        val color = MaterialTheme.colorScheme.surfaceVariant
                        val textStyle = MaterialTheme.typography.bodyLarge
                        LineChart(
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = textStyle,
                                padding = 5.dp,
                                indicators = generateSequence(indicatorsCount*5.0) {
                                        previous -> previous - 5
                                }.takeWhile { it >= 0 }.toList()
                            ),
                            gridProperties = GridProperties(
                                xAxisProperties = GridProperties.AxisProperties()
                            ),
                            labelHelperProperties = LabelHelperProperties(
                                enabled = false,
                            ),
                            labelProperties = LabelProperties(
                                enabled = true,
                                rotation = LabelProperties.Rotation(
                                    degree = -90F,
                                    mode = LabelProperties.Rotation.Mode.Force,
//                                  padding = 50.dp
                                ),
//                              labels = stringDates,
                                padding = 0.dp
                            ),
                            data = remember {
                                listOf(
                                    Line(
                                        popupProperties = PopupProperties(
                                            enabled = true,
                                            containerColor = color,
                                            mode = PopupProperties.Mode.PointMode(),
                                            textStyle = textStyle,
                                            contentBuilder = { value ->
                                                val data = points.find {
                                                    it.value.toDouble() == value
                                                }!!.key
                                                data.format(formatter)
                                            },
                                            contentVerticalPadding = 2.dp,
                                            contentHorizontalPadding = 5.dp,
                                        ),
                                        label = "",
                                        values = values,
                                        color = SolidColor(Color.Black),
                                        curvedEdges = false,
                                        drawStyle = DrawStyle.Stroke(2.dp),
                                        dotProperties = DotProperties(
                                            enabled = true,
                                            color = SolidColor(Color.White),
                                            strokeWidth = 2.dp,
                                            radius = 3.dp,
                                            strokeColor = SolidColor(Color.Black),
                                            animationSpec = tween(700),
                                        ),
                                    ),
                                )
                            },
                            minValue = 0.0,
                            maxValue = maxValues.plus(0.1*maxValues)
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.not_enough_data_for_graph),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                else -> {
                    GetEmptyChart()
                }
            }
        }
        if (isAllFilled(uiState)) {
            StyledButton(
                text = stringResource(R.string.draw_graphic),
                onClick = {
                    if (isModified(prevState, uiState)) {
                        prevState = uiState
                        sfpResultsViewModel.getGraphicData(
                            startDate = uiState.startDate!!,
                            endDate = uiState.endDate!!,
                            categoryId = uiState.selectedCategoryId!!,
                        )
                    }
                },
                isEnabled = true,
                trailingIcon = R.drawable.line_chart,
                trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                modifier = Modifier.padding(
                    start = screenSizeProvider.getEdgeSpacing(),
                    end = screenSizeProvider.getEdgeSpacing(),
                    bottom = if (!screenSizeProvider.isMediumHeight) 25.dp else 0.dp,
                    top = 10.dp
                )
            )
        } else {
            StyledOutlinedButton(
                text = stringResource(R.string.draw_graphic),
                onClick = {},
                isEnabled = false,
                trailingIcon = R.drawable.line_chart,
                trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                modifier = Modifier.padding(
                    start = screenSizeProvider.getEdgeSpacing(),
                    end = screenSizeProvider.getEdgeSpacing(),
                    bottom = if (!screenSizeProvider.isMediumHeight) 25.dp else 0.dp,
                    top = 10.dp
                )
            )
        }
    }
}

private fun isModified(prevState: SFPResultsGraphicUiState, uiState: SFPResultsGraphicUiState): Boolean {
    return (prevState.selectedCategoryId != uiState.selectedCategoryId
            || prevState.endDate != uiState.endDate
            || prevState.startDate != uiState.startDate)
}

private fun isAllFilled(state: SFPResultsGraphicUiState): Boolean {
    return (state.endDate != null && state.startDate != null && state.selectedCategoryId != null)
}

private fun calculatePeriod(viewModel: SFPResultsGraphicViewModel, option: Int) {
    val now = LocalDate.now()
    viewModel.setEndDate(now)
    val start = when (option) {
        0 -> {
            now.minusMonths(1)
        }
        1 -> {
            now.minusMonths(3)
        }
        2 -> {
            now.minusMonths(6)
        }
        3 -> {
            now.minusMonths(12)
        }
        else -> {
            null
        }
    }
    viewModel.setStartDate(start)
}

fun getMiddleDates(dates: List<LocalDate>, minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
    val size = dates.size

    if (size <= 5) {
        return dates
    }

    val middleCount = (size - 2).coerceAtLeast(3).coerceAtMost(4)
    val middleDates = mutableListOf<LocalDate>(minDate, maxDate)

    val totalDays = ChronoUnit.DAYS.between(minDate, maxDate)
    val step = totalDays / (middleCount + 1)
    val startData = minDate.plusDays(step)
    for (i in 1..middleCount) {
        val middleDate = startData.plusDays(step * i)
        middleDates.add(middleDate)
    }

    return middleDates
}

@Composable
fun GetEmptyChart() {
    val textStyle = MaterialTheme.typography.bodyLarge
    return LineChart(
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = textStyle,
            padding = 5.dp
        ),
        gridProperties = GridProperties(
//                          enabled = false
            xAxisProperties = GridProperties.AxisProperties(
//                              lineCount = 10,
            )
        ),
        popupProperties = PopupProperties(
            enabled = true
        ),
        labelHelperProperties = LabelHelperProperties(
            enabled = false,
        ),
        labelProperties = LabelProperties(
            enabled = true,
            rotation = LabelProperties.Rotation(
                degree = -90F,
                mode = LabelProperties.Rotation.Mode.Force,
//                              padding = 50.dp
            ),
//                          labels = stringDates,
            padding = 0.dp
        ),
        data = remember {
            listOf(
                Line(
                    label = "",
                    values = listOf(),
                    color = SolidColor(Color.Red),
                    curvedEdges = false,
                    drawStyle = DrawStyle.Stroke(2.dp),
                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(Color.White),
                        strokeWidth = 2.dp,
                        radius = 3.dp,
                        strokeColor = SolidColor(Color.Red),
                        animationSpec = tween(700),
                    ),
                ),
            )
        },
        minValue = 0.0,
        maxValue = 10.0
    )
}