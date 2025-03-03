package com.example.sportassistant.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun StyledButtonList(
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (Int, String) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items.forEachIndexed(){ index, item ->
            StyledButtonListItem(
                text = item,
                cornerShape = MaterialTheme.shapes.large,
                border = Border.None,
                onClick = {
                    onClick(index, item)
                }
            )
        }
    }
}