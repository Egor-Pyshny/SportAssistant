package com.example.sportassistant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.sportassistant.data.repository.WindowSizeProvider
import org.koin.android.ext.android.get
import java.util.Locale

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locale = Locale("ru")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

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