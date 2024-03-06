package dev.jay.passgenius.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.theme.OrangePrimary
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun EnterPinLayout(
    pinIndicationList: List<Int>,
    onButtonClick: (String) -> Unit,
    onBackSpaceClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column(modifier = Modifier.background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.enter_login_pin), color = OrangePrimary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        PinIndicator(pinIndicationList)
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            val digitList = generateSequence {
                Random.nextInt(1..9)
            }.distinct().take(9).toList()
            items(digitList.size) {
                SinglePinButton(
                    text = digitList[it].toString()
                ) { buttonText ->
                    onButtonClick(buttonText)
                }
            }
        }
        Row {
            SinglePinButton(modifier = Modifier.weight(1f), isBackSpace = true) { onBackSpaceClick() }
            SinglePinButton(modifier = Modifier.weight(1f), text = "0") { buttonText ->
                onButtonClick(buttonText)
            }
            SinglePinButton(modifier = Modifier.weight(1f), isClear = true) { onClearClick() }
        }
    }
}

@Composable
fun SinglePinButton(
    modifier: Modifier = Modifier,
    forNumPad: Boolean = false,
    text: String = "",
    isBackSpace: Boolean = false,
    isClear: Boolean = false,
    onButtonClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clickable { onButtonClick(text) }, contentAlignment = Alignment.Center
    ) {
        val border = if (forNumPad) BorderStroke(0.dp, Color.Transparent) else BorderStroke(0.5.dp, Color.Gray)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .border(border)
        ) {
            drawRect(
                color = Color.White
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 6.dp),
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        if (isBackSpace) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Backspace,
                contentDescription = "Backspace",
                tint = OrangePrimary
            )
        }
        if (isClear) {
            Icon(imageVector = Icons.Outlined.ClearAll, contentDescription = "Clear", tint = OrangePrimary)
        }
    }
}

@Composable
fun PinIndicator(pinEntered: List<Int>) {
    Card(
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        LazyRow(modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)) {
            items(pinEntered.size) {
                val icon = if (pinEntered[it] != -1) Icons.Filled.Circle else Icons.Outlined.Circle
                Icon(
                    imageVector = icon,
                    contentDescription = "Pin Circle",
                    tint = OrangePrimary,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(horizontal = 3.dp)
                )
            }
        }
    }
}

@Composable
fun PinIndicatorFingerPrint(onEnterPinClick: () -> Unit, onFingerPrintIconClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(contentAlignment = Alignment.CenterEnd) {
        Card(
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, start = 18.dp, end = 50.dp)
                    .clickable(
                        onClick = { onEnterPinClick() },
                        indication = null,
                        interactionSource = interactionSource
                    )
            ) {
                items(4) {
                    val icon = Icons.Outlined.Circle
                    Icon(
                        imageVector = icon,
                        contentDescription = "Pin Circle",
                        tint = OrangePrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(horizontal = 3.dp)
                    )
                }
            }
        }
        FingerPrintIcon(onFingerPrintIconClick = { onFingerPrintIconClick() })
    }
}

@Composable
fun FingerPrintIcon(onFingerPrintIconClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(end = 5.dp)
            .clickable(
                onClick = { onFingerPrintIconClick() },
                interactionSource = interactionSource,
                indication = null
            ), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(OrangePrimary)
        }
        Icon(
            imageVector = Icons.Outlined.Fingerprint,
            contentDescription = "FingerPrint",
            modifier = Modifier.fillMaxSize(),
            tint = Color.White
        )
    }
}

@Composable
fun NumberPad(
    onButtonClick: (String) -> Unit,
    onBackSpaceClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            val numbList = listOf(7, 8, 9, 4, 5, 6, 1, 2, 3)
            items(numbList.size) {
                SinglePinButton(
                    onButtonClick = { buttonText -> onButtonClick(buttonText) },
                    text = numbList[it].toString(),
                    forNumPad = true
                )
            }
        }
        Row {
            SinglePinButton(modifier = Modifier.weight(1f), isBackSpace = true, forNumPad = true) { onBackSpaceClick() }
            SinglePinButton(modifier = Modifier.weight(1f), text = "0", forNumPad = true) { buttonText ->
                onButtonClick(buttonText)
            }
            SinglePinButton(modifier = Modifier.weight(1f), isClear = true, forNumPad = true) { onClearClick() }
        }
    }
}

@Composable
fun PinIndicatorStar(indicationList: List<Int>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        LazyRow(modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)) {
            items(indicationList.size) {
                val icon = if (indicationList[it] == -1) Icons.Outlined.StarOutline else Icons.Filled.Star
                Icon(imageVector = icon, contentDescription = "Star", tint = OrangePrimary)
            }
        }
    }
}