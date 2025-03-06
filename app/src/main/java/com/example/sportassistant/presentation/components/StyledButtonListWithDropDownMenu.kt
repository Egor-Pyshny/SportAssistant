package com.example.sportassistant.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StyledButtonListWithDropDownMenu(
    items: List<ListItem>,
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    onClick: (Int, ListItem) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items.forEachIndexed(){ index, item ->
            StyledButtonListItemWithDropDown(
                menuItems = menuItems,
                modifier = Modifier,
                item = item,
                cornerShape = MaterialTheme.shapes.large,
                border = Border.None,
                onClick = {
                    onClick(index, item)
                },
            )
        }
    }
}

data class ListItem(
    val key: Any,
    val item: String,
)
