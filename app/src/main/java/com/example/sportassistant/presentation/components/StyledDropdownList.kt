package com.example.sportassistant.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.sportassistant.R
import com.example.sportassistant.domain.model.Coach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledDropdownList(
    coaches: List<Coach>,
    selectedCoach: Coach?,
    onCoachSelected: (Coach) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox (
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedCoach?.fio ?: "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = stringResource(R.string.choose_coach)
                )
            },
            trailingIcon = {
                GetDropdownTrailingIcon(expanded)
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            singleLine = true,
            readOnly = true,
        )

        DropdownMenu (
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            coaches.forEach{ coach ->
                DropdownMenuItem(
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = coach.fio,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                            )
                            Text(
                                text = coach.institution,
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray),
                                maxLines = 1,
                            )
                        }
                    },
                    onClick = {
                        onCoachSelected(coach)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
