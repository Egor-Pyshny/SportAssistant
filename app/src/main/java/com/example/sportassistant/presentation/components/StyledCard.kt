package com.example.sportassistant.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StyledCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title
            )
            Text(
                text = subtitle
            )
        }
    }
}