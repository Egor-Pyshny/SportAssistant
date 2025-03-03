package com.example.sportassistant.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Locale

@Composable
fun RangeDatePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerHeadline(
    selectedStartDateMillis: Long?,
    selectedEndDateMillis: Long?,
    dateFormatter: DatePickerFormatter,
    modifier: Modifier,
    startDatePlaceholder: @Composable () -> Unit,
    endDatePlaceholder: @Composable () -> Unit,
    datesDelimiter: @Composable () -> Unit,
) {
    val defaultLocale = Locale.getDefault()
    val formatterStartDate = dateFormatter.formatDate(
        dateMillis = selectedStartDateMillis,
        locale = defaultLocale
    )

    val formatterEndDate = dateFormatter.formatDate(
        dateMillis = selectedEndDateMillis,
        locale = defaultLocale
    )
    Row(
        modifier = modifier.clearAndSetSemantics {
            liveRegion = LiveRegionMode.Polite
        }.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (formatterStartDate != null) {
            Text(text = formatterStartDate)
        } else {
            startDatePlaceholder()
        }
        Spacer(Modifier.width(4.dp))
        datesDelimiter()
        Spacer(Modifier.width(4.dp))
        if (formatterEndDate != null) {
            Text(text = formatterEndDate)
        } else {
            endDatePlaceholder()
        }
    }
}
