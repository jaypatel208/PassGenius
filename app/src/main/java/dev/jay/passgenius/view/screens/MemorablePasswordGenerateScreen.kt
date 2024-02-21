package dev.jay.passgenius.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
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
            .background(Color.White), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(8f)) {
            MemorablePasswordGenerateText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
            Spacer(modifier = Modifier.height(50.dp))
            AddCharacteristicsComponent(
                characteristicName = stringResource(id = R.string.words),
                initialCharacteristicValue = 3,
                maxCharacteristicValue = 6,
                onValueChanged = { newWordsValue -> memorablePasswordViewModel.updateWordsValue(newWordsValue) },
                isMemorable = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            AddCharacteristicsComponent(
                characteristicName = stringResource(id = R.string.digits),
                initialCharacteristicValue = 1,
                maxCharacteristicValue = 4,
                onValueChanged = { newDigitsValue -> memorablePasswordViewModel.updateDigitsValue(newDigitsValue) },
                isMemorable = true
            )
        }
        Column(modifier = Modifier.weight(2f)) {
            PasswordShowComponent(
                generatedPassword = memorablePasswordViewModel.generatedPassword.value,
                onRegenerate = { memorablePasswordViewModel.reGeneratePassword() },
                onDone = {})
        }
    }
}