package dev.jay.passgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.jay.passgenius.ui.navigation.AppNavigationGraph
import dev.jay.passgenius.ui.theme.PassGeniusTheme
import dev.jay.passgenius.ui.theme.White

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassGeniusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    AppEntryPoint()
                }
            }
        }
    }
}

@Composable
private fun AppEntryPoint() {
    AppNavigationGraph()
}