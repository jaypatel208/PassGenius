package dev.jay.passgenius.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.jay.passgenius.di.models.SnackBarCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    fun copyTextToClipboard(clipboardManager: ClipboardManager, text: String) {
        clipboardManager.setText(AnnotatedString(text))
    }

    fun showSnackBar(
        snackState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        message: String,
        imageVector: ImageVector,
        snackbarDuration: SnackbarDuration
    ) {
        coroutineScope.launch {
            snackState.showSnackbar(
                SnackBarCustom(
                    message = message,
                    imageVector = imageVector,
                    duration = snackbarDuration
                )
            )
        }
    }
}