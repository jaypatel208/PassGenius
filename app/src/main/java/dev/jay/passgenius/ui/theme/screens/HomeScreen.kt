package dev.jay.passgenius.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.jay.passgenius.ui.theme.components.MetricsComponent
import dev.jay.passgenius.ui.theme.components.TopAppBarApp

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        SetUpHomeScreen()
    }
}

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetUpHomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBarApp()
    }) {
        MetricsComponent()
    }
}
