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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
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

@Composable
fun MorePasswordOptions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissReq: (Boolean) -> Unit,
    offset: DpOffset,
    expanded: Boolean
) {
    DropdownMenu(expanded = expanded, onDismissRequest = { onDismissReq(false) }, offset = offset) {
        DropdownMenuItem(text = { Text(text = stringResource(id = R.string.edit)) }, onClick = { onEditClick() })
        DropdownMenuItem(text = { Text(text = stringResource(id = R.string.delete)) }, onClick = { onDeleteClick() })
    }
}

@Composable
fun BottomSheet(
    sheetState: SheetState,
    pinIndicationList: List<Int>,
    onSheet: (Boolean) -> Unit,
    onButtonClick: (String) -> Unit,
    onBackSpaceClick: () -> Unit,
    onClearClick: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onSheet(false) }, sheetState = sheetState, containerColor = Color.White) {
        EnterPinLayout(
            pinIndicationList = pinIndicationList,
            onButtonClick = { buttonText -> onButtonClick(buttonText) },
            onBackSpaceClick = { onBackSpaceClick() },
            onClearClick = { onClearClick() })
    }
}

@Composable
fun FilledButton(text: String, onButtonClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        colors = CardDefaults.cardColors(containerColor = OrangePrimary),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .clickable(onClick = { onButtonClick() }, indication = null, interactionSource = interactionSource)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun ExitAlertDialog(cancel: (Boolean) -> Unit, ok: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = {
            cancel(false)
        },
        title = {
            Text(
                text = stringResource(R.string.close_the_app),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = stringResource(R.string.do_you_want_to_exit_the_app), fontSize = 16.sp)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    ok()
                }) {
                Text(
                    stringResource(R.string.yes),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    cancel(false)
                }) {
                Text(
                    stringResource(R.string.no),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        }
    )
}

@Composable
fun InfoDialog(title: String, info: String, buttonText: String, ok: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = {
        },
        icon = {
            Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info Icon")
        },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = info, fontSize = 16.sp, textAlign = TextAlign.Center)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    ok()
                }) {
                Text(
                    buttonText,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        }
    )
}