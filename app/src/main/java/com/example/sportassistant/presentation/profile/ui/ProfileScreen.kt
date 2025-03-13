package com.example.sportassistant.presentation.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.model.User
import com.example.sportassistant.presentation.components.StyledCard
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.profile.viewmodel.ProfileViewModel
import com.example.sportassistant.presentation.registration.viewmodel.CoachViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    infoViewModel: ProfileInfoViewModel,
    logout: () -> Unit,
    onUserClick: () -> Unit,
    onCoachClick: () -> Unit,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val data by infoViewModel.getMeResponse.observeAsState()

    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 30.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        when (data) {
            is ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiResponse.Success -> {
                val user: User? = (data as ApiResponse.Success).data
                if (user != null) {
                    StyledCard(
                        title = "${user.name} ${user.surname}",
                        subtitle = user.sportType,
                        onClick = onUserClick
                    )
                    StyledCard(
                        title = user.coach.fio,
                        subtitle = user.coach.institution,
                        onClick = onCoachClick
                    )
                }
            }
            is ApiResponse.Failure -> {
                if ((data as ApiResponse.Failure).code == 403) {
                    logout()
                }
            }
            else -> {}
        }
    }

}