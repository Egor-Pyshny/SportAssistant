package com.example.sportassistant.presentation.verification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.data.schemas.auth.requests.VerifyEmailRequest
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.OtpInputField
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.viewmodel.CheckEmailViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import kotlinx.coroutines.delay
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
    val resendVerificationState by viewModel.resendVerificationResponse.observeAsState()
    val preferences: UserPreferencesRepository = get()
    val focusRequester = remember { FocusRequester() }
    var code by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var remainingTime by remember { mutableIntStateOf(120) }
    var isActive by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(key1 = isActive, key2 = remainingTime) {
        if (!isActive && remainingTime > 0) {
            delay(1000)
            remainingTime--
        } else if (remainingTime == 0) {
            isActive = true
        }
    }
    val minutes = remainingTime / 60
    val seconds = remainingTime % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)

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
        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp),
                text = stringResource(R.string.otp_text),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            OtpInputField(
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 15.dp).fillMaxWidth()
                    .focusRequester(focusRequester),
                otpText = code,
                shouldCursorBlink = false,
                onOtpModified = { value, otpFilled ->
                    code = value
                    isOtpFilled = otpFilled
                    viewModel.clearVerificationResponse()
                    if (otpFilled) {
                        keyboardController?.hide()
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(R.string.otp_text_resend),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(bottom = 5.dp)
                        .clickable {
                            isActive = false
                            viewModel.resendVerificationCode(
                                data = ResendCodeRequest(
                                    email = uiState.userMail
                                )
                            )
                        },
                    text = stringResource(R.string.resend_request),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = if (!isActive) Color.LightGray else Color.Blue,
                    textAlign = TextAlign.Center
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isActive) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = stringResource(R.string.too_many_request),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = timeText,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (
                    verificationState is ApiResponse.Failure &&
                    (
                        (verificationState as ApiResponse.Failure).code == 400 ||
                        (verificationState as ApiResponse.Failure).code == 429
                    )
                ) {
                   val text = when ((verificationState as ApiResponse.Failure).code) {
                       400 -> {
                           stringResource(R.string.incorrect_code)
                       }
                       429 -> {
                           stringResource(R.string.too_many_check_requests)
                       }
                       else -> {
                           "Something went wrong..."
                       }
                   }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            if (isOtpFilled) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {
                        viewModel.verifyEmail(
                            data = VerifyEmailRequest(
                                email = uiState.userMail,
                                code = code,
                            )
                        )
                    },
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp, top = 45.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {},
                    isEnabled = false,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp, top = 45.dp)
                )
            }
        }
    }

    when (verificationState) {
        is ApiResponse.Success -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
            LaunchedEffect(Unit) {
                onContinueRegistrationButtonClick()
                preferences.setIsLoggedIn(true)
                preferences.setIsProfileFilled(false)
            }
        }
        else -> {}
    }
}