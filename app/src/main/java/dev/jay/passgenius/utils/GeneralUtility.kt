package dev.jay.passgenius.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object GeneralUtility {
    @Composable
    fun SetStatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = color
            )
        }
    }

    @Composable
    fun getScreenHeightDP(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp.dp
    }

    @Composable
    fun getScreenWidthDP(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp.dp
    }
}