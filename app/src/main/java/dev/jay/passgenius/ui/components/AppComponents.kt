@file:OptIn(ExperimentalMaterial3Api::class)

package dev.jay.passgenius.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CustomAppSnackBar(color: Color, imageVector: ImageVector, message: String, isRtl: Boolean = false) {
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

@Composable
fun CommonOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    label: String,
    isError: Boolean,
    error: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { textFieldValue ->
            onValueChange(textFieldValue)
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray
            )
        },
        supportingText = {
            if (isError) {
                Text(
                    text = error, modifier = Modifier.padding(end = 20.dp),
                    color = Color.Red
                )
            }
        },
        modifier = modifier, label = { Text(text = label) }, colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedBorderColor = OrangePrimary,
            focusedLabelColor = OrangePrimary,
            unfocusedLabelColor = Color.Black,
            focusedTextColor = OrangePrimary,
            unfocusedTextColor = Color.Black
        ),
        singleLine = true,
        isError = isError
    )
}

@Composable
fun CommonAppButton(onClick: () -> Unit, buttonText: String, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) OrangePrimary else Color.Black
    OutlinedButton(
        onClick = {
            onClick()
        },
        interactionSource = interactionSource,
        border = BorderStroke(1.dp, color),
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color)
    ) {
        Text(text = buttonText, modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp))
    }
}

@Composable
fun FieldIndicationText(fieldName: String, color: Color = Color.Black) {
    Text(text = fieldName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = color)
}

@Composable
fun FieldValueText(modifier: Modifier = Modifier, fieldValue: String, color: Color = Color.Black) {
    Text(text = fieldValue, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = color, modifier = modifier)
}

@Composable
fun CustomAnimatedVisibility(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { with(density) { 50.dp.roundToPx() } } + fadeIn(
            initialAlpha = 0.3f
        ) + expandVertically(
            expandFrom = Alignment.Top
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }
}