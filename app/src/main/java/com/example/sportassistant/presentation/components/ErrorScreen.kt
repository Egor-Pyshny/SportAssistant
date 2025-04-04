package com.example.sportassistant.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.presentation.utils.ApiResponse

@Composable
fun ErrorScreen(
    error: ApiResponse.Failure,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (error.code) {
            500 -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
                )
                Text(text = stringResource(R.string.server_error), modifier = Modifier.padding(16.dp))
            }
            403 -> {
                ApplicationState.logout()
            }
            else -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
                )
                Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
                Text(text = "${error.code}")
            }
        }
    }
}