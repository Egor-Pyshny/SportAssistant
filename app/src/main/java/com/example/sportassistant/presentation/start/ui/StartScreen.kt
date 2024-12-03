package com.example.sportassistant.presentation.start.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledOutlinedButton


@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onRegistrationButtonClicked: () -> Unit = {},
    onLogInButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.start_screen_title),
                style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = stringResource(R.string.start_screen_text),
                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Column {
                StyledButton(
                    text = stringResource(R.string.registration_button_text),
                    onClick = onRegistrationButtonClicked,
                    isEnabled = true,
                    modifier = Modifier.padding(bottom = 25.dp)
                )
                StyledOutlinedButton(
                    text = stringResource(R.string.login_button_text),
                    onClick = onLogInButtonClicked,
                    isEnabled = true,
                )
            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview(

) {
    StartScreen()
}