package com.example.sportassistant.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun StyledInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    @DrawableRes leadingIcon: Int,
    @DrawableRes trailingIcon: Int? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    prefix: String? = null,
    suffix: String? = null,
    supportingText: String? = null,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder != null ) { {Text(text = placeholder)} } else null,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        trailingIcon = if (trailingIcon != null && onTrailingIconClick != null) {
            {
                IconButton (onClick = onTrailingIconClick) {
                    Icon(painter = painterResource(id = trailingIcon), null)
                }
            }
        } else null,
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        isError = isError,
        supportingText = if (supportingText != null ) { {Text(text = supportingText)} } else null,
        prefix = if (prefix != null ) { {Text(text = prefix)} } else null,
        suffix = if (suffix != null ) { {Text(text = suffix)} } else null,
    )
}