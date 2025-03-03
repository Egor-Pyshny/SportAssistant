package com.example.sportassistant.presentation.components

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get

@Composable
fun StyledCardTextField(
    value: String,
    @StringRes
    label: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    maxLines: Int = 1,
) {
    Column(
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = LocalTextStyle.current,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = stringResource(label))
                        Spacer(modifier = Modifier.height(0.dp))

                        innerTextField()
                    }
                }
            }
        )
    }
}