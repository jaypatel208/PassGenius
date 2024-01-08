package dev.jay.passgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
import dev.jay.passgenius.ui.components.CharactersComponent
import dev.jay.passgenius.ui.components.PasswordGenerateScreenTopBar
import dev.jay.passgenius.ui.components.PasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.utils.GeneralUtility

@Preview
@Composable
fun PasswordGenerateScreen() {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PasswordGenerateScreenTopBar() }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
        ) {
            PasswordGenerateText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
            Spacer(modifier = Modifier.height(50.dp))
            CharactersComponent()
            Spacer(modifier = Modifier.height(30.dp))
            AddCharacteristicsComponent(characteristicName = "Digits", characteristicValue = 3)
            Spacer(modifier = Modifier.height(30.dp))
            AddCharacteristicsComponent(characteristicName = "Capitals", characteristicValue = 8)
            Spacer(modifier = Modifier.height(30.dp))
            AddCharacteristicsComponent(characteristicName = "Symbols", characteristicValue = 2)
            Spacer(modifier = Modifier.height(50.dp))
            PasswordShowComponent()
        }
    }
}