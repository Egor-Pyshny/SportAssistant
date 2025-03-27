package com.example.sportassistant.presentation.verification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.VerifyEmailRequest
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.viewmodel.CheckEmailViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerificationScreen(
    viewModel: RegistrationViewModel,
    checkEmailViewModel: CheckEmailViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    onContinueRegistrationButtonClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val verificationState by viewModel.verificationResponse.observeAsState()
    val preferences: UserPreferencesRepository = get()

    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 45.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        StyledOutlinedButton(
            text = stringResource(R.string.continue_button_text),
            onClick = {
                viewModel.verifyEmail(
                    data = VerifyEmailRequest(
                        email = uiState.userMail,
                        code = "111111",
                    )
                )
            },
            isEnabled = true,
            trailingIcon = R.drawable.chevron_right,
            trailingIconModifier = Modifier.padding(top = 1.dp),
            modifier = Modifier.padding(bottom = 45.dp, top = 45.dp)
        )
    }

    when (verificationState) {
        is ApiResponse.Success -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
            LaunchedEffect(Unit) {
                onContinueRegistrationButtonClick()
                preferences.setIsLoggedIn(true)
            }
        }
        else -> {}
    }
}