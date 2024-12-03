package com.example.sportassistant.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    leadingIconModifier: Modifier = Modifier,
    trailingIconModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(50.dp),
        onClick = onClick,
        enabled = isEnabled,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        if (leadingIcon != null) {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                modifier = leadingIconModifier,
            )
        }
        Text(
            text = text,
        )
        if (trailingIcon != null) {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = null,
                modifier = trailingIconModifier,
            )
        }
    }
}