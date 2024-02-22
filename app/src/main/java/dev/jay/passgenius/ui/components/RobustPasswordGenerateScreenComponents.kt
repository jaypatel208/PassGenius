package dev.jay.passgenius.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.theme.OrangePrimary
import kotlin.math.roundToInt

@Composable
fun SolidPasswordGenerateText(modifier: Modifier = Modifier, fontSize: Int) {
    Column(modifier = modifier) {
        Text(text = "Create", color = Color.Black, fontSize = fontSize.sp)
        Text(text = "a solid password by", color = Color.Gray, fontSize = fontSize.sp)
        Text(text = "choosing properties", color = Color.Gray, fontSize = fontSize.sp)
    }
}

@Composable
fun CharactersComponent(modifier: Modifier = Modifier, initialLengthValue: Float, onValueChanged: (Float) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Characters", fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            var sliderPosition by remember { mutableFloatStateOf(initialLengthValue) }
            Text(
                text = sliderPosition.toInt().toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it.roundToInt().toFloat()
                    onValueChanged(sliderPosition)
                },
                modifier = Modifier.padding(start = 100.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.Gray
                ),
                valueRange = 12f..24f,
                steps = 12
            )
        }
    }
}

@Composable
fun AddCharacteristicsComponent(
    characteristicName: String,
    initialCharacteristicValue: Int,
    maxCharacteristicValue: Int,
    modifier: Modifier = Modifier,
    isMemorable: Boolean = false,
    onValueChanged: (Int) -> Unit
) {
    var internalCharacteristicValue by remember { mutableStateOf(initialCharacteristicValue) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = characteristicName, fontSize = 16.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = internalCharacteristicValue.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            PlusMinusComponent(
                onIncrement = {
                    if (isMemorable) {
                        if (internalCharacteristicValue < maxCharacteristicValue) {
                            internalCharacteristicValue++
                            onValueChanged(internalCharacteristicValue)
                        }
                    } else {
                        if (maxCharacteristicValue != 0) {
                            internalCharacteristicValue++
                            onValueChanged(internalCharacteristicValue)
                        }
                    }
                },
                onDecrement = {
                    if (isMemorable) {
                        if (internalCharacteristicValue > initialCharacteristicValue) {
                            internalCharacteristicValue--
                            onValueChanged(internalCharacteristicValue)
                        }
                    } else {
                        if (internalCharacteristicValue > 1) {
                            internalCharacteristicValue--
                            onValueChanged(internalCharacteristicValue)
                        }
                    }
                })
        }
    }
}

@Composable
fun PlusMinusComponent(onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Row {
        CircleShapeComponent(
            imageVector = Icons.Outlined.Add,
            boxColor = OrangePrimary,
            iconColor = Color.Black,
            onClick = onIncrement
        )
        Spacer(modifier = Modifier.width(12.dp))
        CircleShapeComponent(
            imageVector = Icons.Outlined.Remove,
            boxColor = Color.Black,
            iconColor = Color.White,
            onClick = onDecrement
        )
    }
}

@Composable
fun CircleShapeComponent(imageVector: ImageVector, boxColor: Color, iconColor: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(boxColor)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = imageVector, contentDescription = "Add", tint = iconColor)
        }
    }
}

@Composable
fun PasswordShowComponent(generatedPassword: String, onRegenerate: () -> Unit, onDone: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 60.dp)
                .weight(8f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val annotatedString = annotatedString(generatedPassword, OrangePrimary, Color.White)
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = annotatedString,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            }
            Icon(
                imageVector = Icons.Outlined.Autorenew,
                contentDescription = "ReGenerate Password",
                tint = if (isPressed) OrangePrimary else Color.Gray,
                modifier = Modifier
                    .padding(start = 3.dp)
                    .clickable(
                        onClick = onRegenerate,
                        indication = null,
                        interactionSource = interactionSource
                    )
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "Password Done",
                tint = OrangePrimary,
                modifier = Modifier
                    .clickable { onDone() }
            )
        }
    }
}

@Composable
private fun annotatedString(generatedPassword: String, colorDigit: Color, colorText: Color) = buildAnnotatedString {
    for (char in generatedPassword) {
        if (char.isDigit()) {
            withStyle(style = SpanStyle(color = colorDigit)) {
                append(char.toString())
            }
        } else {
            withStyle(style = SpanStyle(color = colorText)) {
                append(char.toString())
            }
        }
    }
}

@Composable
fun CopyAndSaveCard(generatedPassword: String, onCopyPassword: () -> Unit, onSavePassword: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 42.dp),
        border = BorderStroke(5.dp, OrangePrimary),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 48.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(8f)) {
                    val annotatedString = annotatedString(
                        generatedPassword = generatedPassword,
                        colorDigit = OrangePrimary,
                        colorText = Color.Black
                    )
                    Text(
                        text = annotatedString,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    val interactionSourceCopy = remember { MutableInteractionSource() }
                    val isPressedCopy by interactionSourceCopy.collectIsPressedAsState()
                    val interactionSourceSave = remember { MutableInteractionSource() }
                    val isPressedSave by interactionSourceSave.collectIsPressedAsState()
                    Icon(
                        imageVector = Icons.Filled.CopyAll,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSourceCopy,
                            indication = null,
                            onClick = {
                                onCopyPassword()
                            }
                        ),
                        contentDescription = "Copy password",
                        tint = if (isPressedCopy) OrangePrimary else Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Save password",
                        modifier = Modifier.clickable(
                            interactionSource = interactionSourceSave,
                            indication = null,
                            onClick = {
                                onSavePassword()
                            }
                        ),
                        tint = if (isPressedSave) OrangePrimary else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun SavePasswordCard(
    generatedPassword: String,
    onSavePassword: (siteName: String, userName: String, generatedPassword: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 42.dp),
        border = BorderStroke(5.dp, OrangePrimary),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 25.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var siteName by remember { mutableStateOf("") }
            var userName by remember { mutableStateOf("") }
            var isErrorInSiteName by remember { mutableStateOf(false) }
            val limitChar = 30

            fun validateSiteName(text: String) {
                isErrorInSiteName = text.isBlank()
            }

            OutlinedTextField(
                value = siteName,
                onValueChange = {
                    siteName = it.take(limitChar)
                    validateSiteName(siteName)
                },
                placeholder = { Text(stringResource(id = R.string.enter_site), color = OrangePrimary) },
                textStyle = TextStyle(color = OrangePrimary),
                isError = isErrorInSiteName,
                supportingText = {
                    if (isErrorInSiteName) {
                        Text(
                            text = stringResource(id = R.string.blank_site_name),
                            modifier = Modifier.padding(end = 40.dp),
                            color = Color.Red
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = OrangePrimary,
                    focusedBorderColor = OrangePrimary,
                    cursorColor = OrangePrimary
                ),
                modifier = Modifier.padding(horizontal = 12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it.take(limitChar) },
                placeholder = { Text(stringResource(id = R.string.enter_username), color = OrangePrimary) },
                textStyle = TextStyle(color = OrangePrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = OrangePrimary,
                    focusedBorderColor = OrangePrimary,
                    cursorColor = OrangePrimary
                ),
                modifier = Modifier.padding(horizontal = 12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = generatedPassword,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedButton(
                onClick = {
                    validateSiteName(siteName)
                    if (!isErrorInSiteName) onSavePassword(
                        siteName,
                        userName,
                        generatedPassword
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = OrangePrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    color = OrangePrimary
                )
            }
        }
    }
}