package com.example.sportassistant.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.theme.homeScreenListBackground

@Composable
fun StyledButtonListItem(
    text: String,
    modifier: Modifier = Modifier,
    cornerShape: CornerBasedShape = RoundedCornerShape(0.dp),
    border: Border = Border.None,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onClick: () -> Unit,
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = cornerShape
            ).drawBehind {
                val borderSize = 2.dp.toPx()
                val borderColor = Color.Black
                if (border == Border.Bottom) {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row (
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = textStyle,
                modifier = Modifier.weight(1f)
                    .padding(start = 10.dp)
            )
            Icon(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = null
            )
        }
    }
}

enum class Border {
    Bottom,
    None,
}
