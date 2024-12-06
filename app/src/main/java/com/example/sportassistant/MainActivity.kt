package com.example.sportassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sportassistant.presentation.RootNavGraph
import com.example.sportassistant.presentation.theme.SportAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportAssistantTheme {
                Surface {
                    RootNavGraph()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SportAssistantAppPreview() {
    SportAssistantTheme() {
        Surface {
            RootNavGraph()
        }
    }
}