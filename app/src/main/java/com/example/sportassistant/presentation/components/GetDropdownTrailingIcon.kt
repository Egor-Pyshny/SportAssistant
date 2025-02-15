package com.example.sportassistant.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.sportassistant.R

@Composable
fun GetDropdownTrailingIcon(
    expanded: Boolean
) {
    if (expanded) {
        Icon(
            painter = painterResource(R.drawable.arrow_up),
            contentDescription = null
        )
    } else {
        Icon(
            painter = painterResource(R.drawable.arrow_down),
            contentDescription = null
        )
    }
}