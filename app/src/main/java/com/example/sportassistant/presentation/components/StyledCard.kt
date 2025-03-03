package com.example.sportassistant.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R

@Composable
fun StyledCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier.fillMaxWidth().background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.large,
        ).clickable {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier.padding(
                end = 10.dp,
                top = 20.dp,
                start = 15.dp,
                bottom = 20.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.big_user),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxHeight(0.7f)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}