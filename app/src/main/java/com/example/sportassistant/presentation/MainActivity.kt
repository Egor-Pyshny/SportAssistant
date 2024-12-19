package com.example.sportassistant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.theme.SportAssistantTheme
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface {
                val screenSize = calculateWindowSizeClass(this)
                val screenSizeProvider: WindowSizeProvider = get()
                screenSizeProvider.setScreenSizeClass(screenSize)
                RootNavGraph()
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SportAssistantAppPreview() {
//    SportAssistantTheme() {
//        Surface {
//            RootNavGraph()
//        }
//    }
//}