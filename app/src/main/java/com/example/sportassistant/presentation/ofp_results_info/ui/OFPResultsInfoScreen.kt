package com.example.sportassistant.presentation.ofp_results_info.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.presentation.competition_add.domain.CompetitionUiState
import com.example.sportassistant.presentation.components.DatePickerHeadline
import com.example.sportassistant.presentation.components.DateRangePickerHeadline
import com.example.sportassistant.presentation.components.DecimalFormatter
import com.example.sportassistant.presentation.components.DecimalInputVisualTransformation
import com.example.sportassistant.presentation.components.GetDropdownTrailingIcon
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.ofp_result_add.domain.OFPResultModelUiState
import com.example.sportassistant.presentation.utils.getCategoryText
import com.example.sportassistant.presentation.ofp_result_add.viewmodel.OFPResultAddViewModel
import com.example.sportassistant.presentation.ofp_results.viewmodel.OFPResultsViewModel
import com.example.sportassistant.presentation.ofp_results_info.viewmodel.OFPResultInfoViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OFPResultsInfoScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    ofpResultsInfoViewModel: OFPResultInfoViewModel = koinViewModel(),
) {
    val state = ApplicationState.getState()
    LaunchedEffect(Unit) {
        ofpResultsInfoViewModel.getOFPResultInfo(state.OFP!!.id)
        ofpResultsInfoViewModel.getCategories()
    }
    val ofpInfoResponse by getResults(ofpResultsInfoViewModel)
    val ofpUpdateResponse by ofpResultsInfoViewModel.updateOFPResultResponse.observeAsState()
    val categoriesResponse by ofpResultsInfoViewModel.getCategoriesResponse.observeAsState()
    var prevState by remember { mutableStateOf<OFPResult?>(null) }
    val uiState by ofpResultsInfoViewModel.uiState.collectAsState()
    var missingDate by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input,
    )
    val decimalFormatter = DecimalFormatter()
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (ofpInfoResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                LaunchedEffect(Unit) {
                    val ofpData = (ofpInfoResponse as ApiResponse.Success<OFPResult?>).data!!
                    prevState = ofpData
                    val regex = Regex("^(.*)\\.0+$")
                    val matchResult = regex.matchEntire(ofpData.result.toString())
                    val newResult = matchResult?.groupValues?.get(1)
                    ofpResultsInfoViewModel.setCategory(ofpData.categoryId)
                    ofpResultsInfoViewModel.setNotes(ofpData.notes)
                    ofpResultsInfoViewModel.setDate(ofpData.date)
                    ofpResultsInfoViewModel.setResult(newResult ?: ofpData.result.toString())
                    ofpResultsInfoViewModel.setGoals(ofpData.goals)
                    val dateMillis = ofpData.date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    datePickerState.selectedDateMillis = dateMillis
                }
                val categories = (categoriesResponse as ApiResponse.Success<List<CategoryModel>?>).data
                    ?: listOf()
                if (state.OFPCategories == null) {
                    ApplicationState.setOFPCategories(categories)
                }
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.fillMaxWidth().padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 6.dp,
                        )
                    ) {
                        StyledCardTextField(
                            value = getCategoryText(uiState.categoryId, categories),
                            label = R.string.ofp_test_name,
                            onValueChange = { },
                            enabled = false,
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            trailingIcon = {
                                GetDropdownTrailingIcon(expanded)
                            },
                        )
                        DropdownMenu (
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.exposedDropdownSize().heightIn(max = 200.dp)
                        ) {
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
                                        ofpResultsInfoViewModel.setCategory(option.id)
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    HorizontalDivider(thickness = 2.dp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                showDialog = true
                                missingDate = false
                            }
                    ) {
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

                        val date = datePickerState.selectedDateMillis?.let {
                            val date = LocalDate.ofInstant(
                                Instant.ofEpochMilli(it),
                                ZoneId.systemDefault()
                            )
                            ofpResultsInfoViewModel.setDate(date)
                            date.format(formatter)
                        } ?: ""

                        StyledCardTextField(
                            value = date,
                            label = R.string.add_competition_date,
                            onValueChange = { },
                            enabled = false,
                            modifier = Modifier.padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 15.dp,
                                bottom = 6.dp,
                            )
                        )
                    }
                    if (showDialog) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDialog = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    if (
                                        datePickerState.selectedDateMillis != null
                                    ) {
                                        showDialog = false
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
                                    datePickerState.selectedDateMillis = System.currentTimeMillis()
                                    ofpResultsInfoViewModel.setDate(LocalDate.now())
                                }) {
                                    Text(stringResource(R.string.cancel))
                                }
                            }
                        ) {
                            DatePicker(
                                state = datePickerState,
                                modifier = Modifier,
                                showModeToggle = false,
                                title = {},
                                headline = {
                                    DatePickerHeadline(
                                        selectedDateMillis = datePickerState.selectedDateMillis,
                                        dateFormatter = DatePickerDefaults.dateFormatter(),
                                        modifier = Modifier.padding(
                                            start = 15.dp,
                                            end = 12.dp,
                                            bottom = 12.dp,
                                        ),
                                        datePlaceholder = { Text(text = stringResource(R.string.date_placeholder)) },
                                    )
                                },
                            )
                            if (missingDate) {
                                val text = stringResource(R.string.missing_ofp_date_error)
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
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.result,
                        label = R.string.ofp_test_result,
                        onValueChange = {
                            val formatedText = decimalFormatter.cleanup(it)
                            if (formatedText.toFloatOrNull() != null){
                                ofpResultsInfoViewModel.setResult(formatedText)
                            } else if (formatedText.toFloatOrNull() == null && it == "") {
                                ofpResultsInfoViewModel.setResult("")
                            }
                        },
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 6.dp,
                        ),
                        keyboardType = KeyboardType.Number,
                        visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
                    )
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.goals,
                        label = R.string.add_camp_goals,
                        onValueChange = {
                            ofpResultsInfoViewModel.setGoals(it)
                        },
                        singleLine = false,
                        maxLines = 3,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 15.dp,
                        )
                    )
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.notes,
                        label = R.string.add_competition_notes,
                        onValueChange = {
                            ofpResultsInfoViewModel.setNotes(it)
                        },
                        singleLine = false,
                        maxLines = 5,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 15.dp,
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isAllFilled(uiState) && isModified(uiState, prevState!!)) {
                        StyledButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {
                                ofpResultsInfoViewModel.updateOFPResult(
                                    data = OFPResultsCreateRequest(
                                        ofpCategoryId = uiState.categoryId!!,
                                        date = uiState.date!!,
                                        result = uiState.result.toFloat(),
                                        notes = uiState.notes,
                                        goals = uiState.goals,
                                    ),
                                    ofpId = prevState!!.id,
                                )
                            },
                            isEnabled = true,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 25.dp)
                        )
                    } else {
                        StyledOutlinedButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {},
                            isEnabled = false,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 25.dp)
                        )
                    }
                }
            }
            is ApiResponse.Failure -> {
                Text(
                    text = (ofpInfoResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }

    when (ofpUpdateResponse) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                val newState = OFPResult(
                    id = prevState!!.id,
                    categoryId = uiState.categoryId!!,
                    date = uiState.date!!,
                    result = uiState.result.toFloat(),
                    notes = uiState.notes,
                    goals = uiState.goals
                )
                prevState = newState
                ofpResultsInfoViewModel.clearUpdateResponse()
            }
        }
        else -> {}
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun getResults(
    ofpResultsViewModel: OFPResultInfoViewModel,
): State<ApiResponse<OFPResult?>?> {
    val getCategoriesState by ofpResultsViewModel.getCategoriesResponse.observeAsState()
    val getInfoState by ofpResultsViewModel.getOFPResultInfoResponse.observeAsState()

    when (getCategoriesState) {
        is ApiResponse.Loading -> {
            return mutableStateOf(ApiResponse.Loading)
        }
        is ApiResponse.Success -> {
            when (getInfoState) {
                is ApiResponse.Loading -> {
                    return mutableStateOf(ApiResponse.Loading)
                }
                is ApiResponse.Success -> {
                    return ofpResultsViewModel.getOFPResultInfoResponse.observeAsState()
                }
                else -> {throw Error()}
            }
        }
        null -> {
            return mutableStateOf(ApiResponse.Loading)
        }
        else -> {throw Error()}
    }
}

private fun isAllFilled(state: OFPResultModelUiState): Boolean {
    return (state.categoryId != null
            && state.goals.isNotEmpty()
            && state.date != null
            && state.result.toFloatOrNull() != null)
}

private fun isModified(state: OFPResultModelUiState, prevState: OFPResult): Boolean {
    val regex = Regex("^(.*)\\.0+$")
    val prevMatchResult = regex.matchEntire(prevState.result.toString())
    val prevResult = prevMatchResult?.groupValues?.get(1) ?: prevState.result.toString()

    val currentMatchResult = regex.matchEntire(state.result)
    val currentResult = currentMatchResult?.groupValues?.get(1) ?: state.result
    if (currentResult != prevResult && currentResult.endsWith('.')) {
        val temp = currentResult.dropLast(1)
        return (state.categoryId != prevState.categoryId
                || state.date != prevState.date
                || state.notes != prevState.notes
                || state.goals != prevState.goals
                || (temp != prevResult))
    }
    return (state.categoryId != prevState.categoryId
            || state.date != prevState.date
            || state.notes != prevState.notes
            || state.goals != prevState.goals
            || (currentResult != prevResult))
}