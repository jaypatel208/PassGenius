@file:OptIn(ExperimentalMaterial3Api::class)

package dev.jay.passgenius.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.jay.passgenius.ui.theme.OrangePrimary

@Composable
fun AppTopBar(title: String, icon: ImageVector, onBack: () -> Unit) {
    TopAppBar(title = { }, actions = {
        Text(text = title, color = Color.Black)
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.padding(end = 16.dp, start = 8.dp),
            tint = Color.Black
        )
    }, navigationIcon = {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        Icon(
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = "Back",
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable(onClick = onBack, indication = null, interactionSource = interactionSource),
            tint = if (isPressed) OrangePrimary else Color.Black
        )
    })
}

@Composable
fun CustomAppSnackBar(color: Color,imageVector: ImageVector, message: String, isRtl: Boolean = false) {
    Snackbar(containerColor = OrangePrimary, modifier = Modifier.padding(16.dp)) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = color
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(message, color = color)
            }
        }
    }
}