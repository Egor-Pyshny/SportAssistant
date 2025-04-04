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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCoachesDropdownList
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.domain.model.ProfileInfoUiState
import com.example.sportassistant.presentation.registration.viewmodel.CoachViewModel
import com.example.sportassistant.presentation.registration.viewmodel.SetProfileInfoViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegistrationCoachScreen(
    viewModel: SetProfileInfoViewModel,
    coachViewModel: CoachViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onFinishRegistrationButtonClick: () -> Unit = {},
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coaches by coachViewModel.coachesResponse.observeAsState()
    val setInfoState by viewModel.setInfoResponse.observeAsState()

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
                StyledCoachesDropdownList(
                    coaches = coaches,
                    selectedCoach = uiState.selectedCoach,
                    onCoachSelected = {
                        viewModel.setCoach(it)
                    }
                )
                if (setInfoState is ApiResponse.Failure) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        text = (setInfoState as ApiResponse.Failure).errorMessage
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
                        viewModel.setInfo(
                            SetProfileDataRequest(
                                sportType = uiState.sportType,
                                qualification = uiState.qualification,
                                address = uiState.address,
                                phoneNumber = uiState.phoneNumber,
                                sex = uiState.gender,
                                coachId = uiState.selectedCoach!!.id,
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
    when (setInfoState) {
        is ApiResponse.Loading -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
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
                onFinishRegistrationButtonClick()
            }
        }
        else -> {}
    }

}

private fun isAllFilled(state: ProfileInfoUiState): Boolean {
//    return true
    return state.selectedCoach != null
}
