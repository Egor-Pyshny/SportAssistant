package com.example.sportassistant.presentation.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.RegistrationRequest
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledDropdownList
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import com.example.sportassistant.presentation.registration.viewmodel.CoachViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegistrationCoachScreen(
    viewModel: RegistrationViewModel,
    coachViewModel: CoachViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onFinishRegistrationButtonClick: () -> Unit = {},
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coaches by coachViewModel.coachesResponse.observeAsState()
    val registrationState by viewModel.registrationResponse.observeAsState()
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
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.create_coach_text),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Image(
                    modifier = Modifier.height(screenSizeProvider.getScreenDimensions().screenHeight*0.27f),
                    painter = painterResource(R.drawable.coach_screen),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                StyledDropdownList(
                    coaches = if (coaches is ApiResponse.Success) {(coaches as ApiResponse.Success<List<Coach>?>).data ?: listOf()} else listOf(),
                    selectedCoach = uiState.selectedCoach,
                    onCoachSelected = {
                        viewModel.setCoach(it)
                    }
                )
                if (registrationState is ApiResponse.Failure) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        text = (registrationState as ApiResponse.Failure).errorMessage
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {
                        viewModel.registration(
                            RegistrationRequest(
                                name = uiState.userName,
                                surname = uiState.userSurname,
                                sportType = uiState.sportType,
                                qualification = uiState.qualification,
                                address = uiState.address,
                                phoneNumber = uiState.phoneNumber,
                                sex = uiState.gender,
                                coachId = uiState.selectedCoach!!.id,
                                email = uiState.userMail,
                                password = uiState.userPassword
                            )
                        )
                    },
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {},
                    isEnabled = false,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp)
                )
            }
        }
    }
    when (registrationState) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            LaunchedEffect(Unit) {
                preferences.setIsLoggedIn(true)
                onFinishRegistrationButtonClick()
            }
        }
        else -> {}
    }

}

private fun isAllFilled(state: RegistrationUiState): Boolean {
//    return true
    return state.selectedCoach != null
}
