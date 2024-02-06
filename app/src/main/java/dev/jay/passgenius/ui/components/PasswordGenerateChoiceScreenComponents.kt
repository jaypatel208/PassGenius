package dev.jay.passgenius.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.ui.theme.OrangePrimary

@Composable
fun PasswordChoiceText(modifier: Modifier = Modifier, fontSize: Int) {
    Column(modifier = modifier) {
        Text(text = "Secure Your World:", color = Color.Black, fontSize = fontSize.sp)
        Text(text = "Pick Your", color = Color.Gray, fontSize = fontSize.sp)
        Text(text = "Password Style", color = Color.Gray, fontSize = fontSize.sp)
    }
}

@Composable
fun PasswordChoiceCard(modifier: Modifier = Modifier, title: String, choiceClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(
                width = 3.dp, color = if (isPressed) OrangePrimary else Color.Black,
                shape = RoundedCornerShape(size = 30.dp)
            )
            .clickable(enabled = true, onClick = choiceClick, interactionSource = interactionSource, indication = null)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, color = if (isPressed) OrangePrimary else Color.Black, fontSize = 28.sp)
        }
    }
}