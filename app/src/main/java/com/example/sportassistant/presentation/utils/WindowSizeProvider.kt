package com.example.sportassistant.data.repository

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class WindowSizeProvider() {
    private var size: WindowSizeClass? = null
    private var edgesMargin: Dp = 24.dp

    @Composable
    fun getScreenDimensions(): ScreenDimensions {
        val configuration = LocalConfiguration.current
        val density = LocalDensity.current

        val screenHeight = with(density) { configuration.screenHeightDp.dp }
        val screenWidth = with(density) { configuration.screenWidthDp.dp }

        return ScreenDimensions(screenHeight, screenWidth)
    }

    fun setScreenSizeClass(size: WindowSizeClass?) {
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

data class ScreenDimensions(val screenHeight: Dp, val screenWidth: Dp)

