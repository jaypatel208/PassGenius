package dev.jay.passgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jay.passgenius.TemporaryConstants
import dev.jay.passgenius.ui.components.BottomBar
import dev.jay.passgenius.ui.components.HomeScreenTopBar
import dev.jay.passgenius.ui.components.MetricsComponent
import dev.jay.passgenius.ui.components.PasswordsLazyColumn
import dev.jay.passgenius.ui.theme.OrangeMetrics
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.utils.PasswordUtility

@Composable
fun HomeScreen() {
    val passwords = PasswordUtility.categorizePasswords(TemporaryConstants.passwords)
    GeneralUtility.SetStatusBarColor(color = OrangeMetrics)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeScreenTopBar() },
        bottomBar = { BottomBar() }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).background(Color.White)) {
            MetricsComponent(totalPasswords = "58", strongPasswords = "47", mediocrePasswords = "11")
            PasswordsLazyColumn(
                passwords,
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp).background(Color.White)
            )
        }
    }
}