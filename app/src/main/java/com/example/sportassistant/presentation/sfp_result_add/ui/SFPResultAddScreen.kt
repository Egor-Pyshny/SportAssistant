package com.example.sportassistant.presentation.sfp_result_add.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.DatePickerHeadline
import com.example.sportassistant.presentation.components.DecimalFormatter
import com.example.sportassistant.presentation.components.DecimalInputVisualTransformation
import com.example.sportassistant.presentation.components.ErrorScreen
import com.example.sportassistant.presentation.components.GetDropdownTrailingIcon
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.SingleButtonDialog
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.sfp_result_add.viewmodel.SFPResultAddViewModel
import com.example.sportassistant.presentation.sfp_result_add.domain.SFPResultModelUiState
import com.example.sportassistant.presentation.sfp_results.viewmodel.SFPResultsViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.getCategoryText
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SFPResultAddScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    sfpResultsAddViewModel: SFPResultAddViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        sfpResultsAddViewModel.getCategories()
    }
    val uiState by sfpResultsAddViewModel.uiState.collectAsState()
    val sfpAddState by sfpResultsAddViewModel.sfpAddResponse.observeAsState()
    val categoriesResponse by sfpResultsAddViewModel.getCategoriesResponse.observeAsState()
    var missingDate by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input,
    )
    val decimalFormatter = DecimalFormatter()
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (categoriesResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                val categories = (categoriesResponse as ApiResponse.Success<List<CategoryModel>?>).data
                    ?: listOf()
                val state = ApplicationState.getState()
                if (state.SFPCategories == null) {
                    ApplicationState.setSFPCategories(categories)
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
                            label = R.string.sfp_test_name,
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
                                        sfpResultsAddViewModel.setCategory(option.id)
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
                            sfpResultsAddViewModel.setDate(date)
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
                                    sfpResultsAddViewModel.setDate(LocalDate.now())
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
                                val text = stringResource(R.string.missing_sfp_date_error)
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
                        label = R.string.sfp_test_result,
                        onValueChange = {
                            val formatedText = decimalFormatter.cleanup(it)
                            if (formatedText.toFloatOrNull() != null) {
                                sfpResultsAddViewModel.setResult(formatedText)
                            }
                            if (it == "") {
                                sfpResultsAddViewModel.setResult("")
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
                            sfpResultsAddViewModel.setGoals(it)
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
                            sfpResultsAddViewModel.setNotes(it)
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
                    if (isAllFilled(uiState)) {
                        StyledButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {
                                sfpResultsAddViewModel.addSFPResult(
                                    SFPResultsCreateRequest(
                                        sfpCategoryId = uiState.categoryId!!,
                                        date = uiState.date!!,
                                        result = uiState.result.toFloat(),
                                        notes = uiState.notes,
                                        goals = uiState.goals,
                                    )
                                )
                            },
                            isEnabled = true,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 45.dp)
                        )
                    } else {
                        StyledOutlinedButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {},
                            isEnabled = false,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 45.dp)
                        )
                    }
                }
            }
            null -> { Loader() }
            is ApiResponse.Failure -> {
                ErrorScreen(categoriesResponse as ApiResponse.Failure)
            }
        }
    }

    when (sfpAddState) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            Loader()
            LaunchedEffect(Unit) {
                navController.navigate(HomeRoutes.SFPResults.route) {
                    popUpTo(HomeRoutes.SFPResults.route) {
                        inclusive = true
                    }
                }
            }
        }
        is ApiResponse.Failure -> {
            var showErrorDialog by remember { mutableStateOf(false) }
            SingleButtonDialog(
                showDialog = showErrorDialog,
                onDismiss = { showErrorDialog = false },
                title = stringResource(R.string.error_notification_title),
                message = stringResource(R.string.add_error_notification_text)
            )
        }
        else -> {}
    }
}

private fun isAllFilled(state: SFPResultModelUiState): Boolean {
    return (state.categoryId != null
            && state.goals.isNotEmpty()
            && state.date != null
            && state.result.toFloatOrNull() != null)
}