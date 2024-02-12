package dev.jay.passgenius.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.ui.components.MemorablePasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.viewmodel.MemorablePasswordViewModel

@Composable
fun MemorablePasswordGenerateScreen(
    innerPadding: PaddingValues,
    memorablePasswordViewModel: MemorablePasswordViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
    ) {
        MemorablePasswordGenerateText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
        Spacer(modifier = Modifier.height(50.dp))
        PasswordShowComponent(
            generatedPassword = memorablePasswordViewModel.generatedPassword.value,
            onRegenerate = {memorablePasswordViewModel.initGeneratePassword()},
            onDone = {})
    }
}