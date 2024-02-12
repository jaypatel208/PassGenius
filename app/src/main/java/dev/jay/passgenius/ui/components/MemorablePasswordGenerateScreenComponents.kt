package dev.jay.passgenius.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MemorablePasswordGenerateText(modifier: Modifier = Modifier, fontSize: Int) {
    Column(modifier = modifier) {
        Text(text = "Create", color = Color.Black, fontSize = fontSize.sp)
        Text(text = "a memorable password", color = Color.Gray, fontSize = fontSize.sp)
        Text(text = "by choosing properties", color = Color.Gray, fontSize = fontSize.sp)
    }
}