package dev.jay.passgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdsClick
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jay.passgenius.ui.components.PasswordChoiceText
import dev.jay.passgenius.ui.components.PasswordGenerateScreenTopBar
import dev.jay.passgenius.utils.GeneralUtility

@Composable
fun PasswordChoiceScreen() {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PasswordGenerateScreenTopBar(
                title = "Select Password Type",
                icon = Icons.Outlined.AdsClick
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
        ) {
            PasswordChoiceText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
        }
    }
}