package com.example.sportassistant.presentation.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get


@Composable
fun CoachInfoScreen(
    infoViewModel: ProfileInfoViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val coachInfo by infoViewModel.getMeResponse.observeAsState()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 15.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.height(screenSizeProvider.getScreenDimensions().screenHeight * 0.2f),
                    painter = painterResource(R.drawable.user_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                if (coachInfo is ApiResponse.Success) {
                    val data = (coachInfo as ApiResponse.Success).data
                    StyledInput(
                        value = data?.coach!!.fio,
                        onValueChange = {},
                        leadingIcon = R.drawable.user_icon,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                    )
                    StyledInput(
                        value = data.coach.phoneNumber,
                        onValueChange = {},
                        leadingIcon = R.drawable.phone,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                    )
                    StyledInput(
                        value = data.coach.institution,
                        onValueChange = {},
                        leadingIcon = R.drawable.institution,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                    )
                }
            }
        }
    }
}