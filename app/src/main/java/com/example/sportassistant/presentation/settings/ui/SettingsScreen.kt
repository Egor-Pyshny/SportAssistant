package com.example.sportassistant.presentation.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.StyledButtonListItem
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(
    navController: NavHostController,
    screenSizeProvider: WindowSizeProvider = get(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
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
            text = stringResource(R.string.home_list_item_premium),
            cornerShape = MaterialTheme.shapes.large,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp
            ),
            onClick = {
                navController.navigate(HomeRoutes.Premium.route)
            },
            modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
        )

        StyledButtonListItem (
            text = stringResource(R.string.home_list_item_layout),
            cornerShape = MaterialTheme.shapes.large.copy(
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp),
            ),
            border = Border.Bottom,
            onClick = {
                navController.navigate(HomeRoutes.LayoutSettings.route)
            },
        )
        StyledButtonListItem (
            text = stringResource(R.string.home_list_item_about_app),
            cornerShape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
            ),
            onClick = {
                navController.navigate(HomeRoutes.AboutApp.route)
            },
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Text(
            text = stringResource(R.string.database_text),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

         StyledButtonListItem (
            text = stringResource(R.string.home_list_item_ofp),
            cornerShape = MaterialTheme.shapes.large.copy(
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp),
            ),
            border = Border.Bottom,
            onClick = { },
            modifier = Modifier.padding(top = 20.dp)
        )
        StyledButtonListItem (
            text = stringResource(R.string.home_list_item_cfp),
            cornerShape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
            ),
            onClick = { },
            modifier = Modifier.padding(bottom = 20.dp)
        )
    }
}