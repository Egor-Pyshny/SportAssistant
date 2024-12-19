package com.example.sportassistant.presentation.start.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import org.koin.androidx.compose.get


@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    onRegistrationButtonClicked: () -> Unit = {},
    onLogInButtonClicked: () -> Unit = {},
) {
    val topImage = if (screenSizeProvider.isMediumHeight){
        30.dp
    } else {
        80.dp
    }
    val topText = if (screenSizeProvider.isMediumHeight){
        10.dp
    } else {
        40.dp
    }
    Surface (
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = modifier.fillMaxSize()
                .padding(start = screenSizeProvider.getEdgeSpacing(),
                        end = screenSizeProvider.getEdgeSpacing()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.start_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f).padding(top = topImage)
            )
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(top = topText),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.start_screen_title),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = stringResource(R.string.start_screen_text),
                        style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Column(
                    modifier = Modifier.padding(bottom = 60.dp)
                ) {
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
}

@Preview
@Composable
fun StartScreenPreview(

) {
    StartScreen()
}