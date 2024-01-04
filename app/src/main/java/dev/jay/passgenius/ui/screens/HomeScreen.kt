package dev.jay.passgenius.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jay.passgenius.ui.components.BottomBar
import dev.jay.passgenius.ui.components.MetricsComponent
import dev.jay.passgenius.ui.components.TopBar
import dev.jay.passgenius.ui.theme.OrangeMetrics
import dev.jay.passgenius.utils.GeneralUtility

@Composable
fun HomeScreen() {
    GeneralUtility.SetStatusBarColor(color = OrangeMetrics)
    SetUpHomeScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetUpHomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() },
        bottomBar = { BottomBar() }) {
        MetricsComponent(totalPasswords = "58", strongPasswords = "47", mediocrePasswords = "11")
    }
}