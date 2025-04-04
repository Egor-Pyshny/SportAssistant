package com.example.sportassistant.presentation.components

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
                modifier = Modifier,
                text = item,
                cornerShape = MaterialTheme.shapes.large,
                border = Border.None,
                onClick = {
                    onClick(index, item)
                },
            )
        }
    }
}