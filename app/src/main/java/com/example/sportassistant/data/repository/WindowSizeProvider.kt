package com.example.sportassistant.data.repository

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class WindowSizeProvider() {
    private var size: WindowSizeClass? = null
    private var edgesMargin: Dp = 24.dp

    fun setScreenSize(size: WindowSizeClass?) {
        this.size = size
        this.edgesMargin = if (size?.widthSizeClass == WindowWidthSizeClass.Compact){
            16.dp
        } else {
            24.dp
        }
    }

    fun getEdgeSpacing(): Dp {
        return this.edgesMargin
    }

    val isCompactHeight: Boolean get() = size?.heightSizeClass == WindowHeightSizeClass.Compact
    val isMediumHeight: Boolean get() = size?.heightSizeClass == WindowHeightSizeClass.Medium
    val isExpandedHeight: Boolean get() = size?.heightSizeClass == WindowHeightSizeClass.Expanded
}