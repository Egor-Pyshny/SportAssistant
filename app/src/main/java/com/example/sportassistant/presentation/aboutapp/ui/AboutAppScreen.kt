package com.example.sportassistant.presentation.aboutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.StyledButtonListItem
import org.koin.androidx.compose.get

@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        StyledButtonListItem (
            text = stringResource(R.string.home_list_item_privacy_and_policy),
            cornerShape = MaterialTheme.shapes.large.copy(
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp),
            ),
            border = Border.Bottom,
            onClick = { },
            modifier = Modifier.padding(top = 20.dp)
        )
        StyledButtonListItem (
            text = stringResource(R.string.home_list_item_rules)
            ,
            cornerShape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
            ),
            onClick = { },
            modifier = Modifier.padding(bottom = 20.dp)
        )
    }
}