package dev.jay.passgenius.di.models

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.vector.ImageVector

data class SnackBarCustom(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration,
    override val message: String,
    override val withDismissAction: Boolean = false,
    val imageVector: ImageVector
) : SnackbarVisuals
