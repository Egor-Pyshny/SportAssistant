package com.example.sportassistant.presentation.components

import android.util.Log
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.theme.homeScreenListBackground

@Composable
fun StyledButtonListItemWithDropDown(
    item: ListItem,
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    cornerShape: CornerBasedShape = RoundedCornerShape(0.dp),
    border: Border = Border.None,
    onClick: () -> Unit,
) {
    var itemHeight by remember { mutableStateOf(0.dp) }
    var menuPosition by remember { mutableStateOf(DpOffset.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    var showMenu by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = cornerShape
            )
            .indication(interactionSource, LocalIndication.current)
            .drawBehind {
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
            }.pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { offset ->
                        menuPosition = DpOffset(offset.x.toDp(), offset.y.toDp())
                        showMenu = true
                    },
                    onTap = {onClick()},
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }.onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row (
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.item,
                modifier = Modifier.weight(1f)
                    .padding(start = 10.dp)
            )
            Icon(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = null
            )
        }
        if (showMenu) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { showMenu = false },
                offset = menuPosition.copy(
                    y = menuPosition.y - itemHeight
                ),
            ) {
                menuItems.forEachIndexed() { index, it ->
                    DropdownMenuItem(
                        onClick = {
                            it.onClick(item)
                            showMenu = false
                        },
                        text = {
                            Text(it.text)
                        }
                    )
                    if (index != menuItems.size - 1)
                        HorizontalDivider(thickness = 1.dp)
                }
            }
        }
    }
}

data class MenuItem(
    val text: String,
    val onClick: (ListItem) -> Unit,
)
