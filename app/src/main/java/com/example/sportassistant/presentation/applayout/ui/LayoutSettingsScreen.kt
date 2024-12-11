package com.example.sportassistant.presentation.applayout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.applayout.viewmodel.testV
import com.example.sportassistant.presentation.theme.homeScreenListBackground
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun LayoutSettingsScreen(
    userPreferencesRepository: UserPreferencesRepository = get(),
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val coroutineScope = rememberCoroutineScope()
    val isDark by userPreferencesRepository.isDarkTheme.collectAsState(initial = false)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 20.dp,
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onTertiary,
                    shape = MaterialTheme.shapes.large
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row (
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_list_item_night_mode),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                )
                Switch(
                    checked = isDark,
                    onCheckedChange = {
                        coroutineScope.launch {
                            userPreferencesRepository.saveLayoutPreference(!isDark)
                        }
                    },
                    thumbContent = {},
                    colors = SwitchDefaults.colors(
                        uncheckedBorderColor = Color.Transparent,
                    )
                )
            }
        }
    }
}